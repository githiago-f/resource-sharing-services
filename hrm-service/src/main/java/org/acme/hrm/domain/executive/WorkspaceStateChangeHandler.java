package org.acme.hrm.domain.executive;

import io.smallrye.reactive.messaging.annotations.Blocking;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.util.UUID;

import org.acme.hrm.domain.executive.workspace.Workspace;
import org.acme.hrm.domain.executive.workspace.vo.WorkspaceState;
import org.acme.hrm.domain.executive.workspace.WorkspaceRepository;
import org.acme.hrm.domain.executive.exceptions.WorkspaceNotFoundException;

import jakarta.inject.Inject;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;
import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.reactive.panache.common.WithSession;

@Slf4j
@ApplicationScoped
public class WorkspaceStateChangeHandler {
    private final Long TIME_TO_LIVE = 1300l;
    private Long currentRuningMessageCounter = 0l;
    @Inject WorkspaceRepository workspaceRepository;

    private String getWorkspaceId(String message) {
        return message.split(":")[0];
    }

    private Workspace allocateFor(Workspace workspace) {
        try {
            // TODO implement real feature
            WorkspaceState state;
            switch(workspace.getState()) {
                case STARTING:
                    log.info("Allocating resource for running {}", workspace.toString());
                    state = WorkspaceState.RUNNING;
                    break;
                case STOPPING:
                    state = WorkspaceState.STOPED;
                    break;
                case RUNNING:
                    log.info("Check if VM is IDLE (mocked)");
                    if(currentRuningMessageCounter % 4 == 0) {
                        state = WorkspaceState.IDLE;
                        log.info("VM marked to shutdown in {} seconds", TIME_TO_LIVE);
                        // TODO save event on shutdown queue table
                        break;
                    }
                case IDLE:
                    state = WorkspaceState.STOPPING;
                default: state = workspace.getState();
                    break;
            }
            Thread.sleep(5000);
            return workspace.withState(state);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @WithSession
    public Uni<Workspace> findWorkspace(UUID uuid) {
        return workspaceRepository.findForUpdate(uuid).onItem()
            .ifNull().failWith(new WorkspaceNotFoundException(uuid))
            .onItem().ifNotNull().transform(this::allocateFor)
            .onItem().ifNotNull().call(workspace -> {
                var updateRequest = workspaceRepository.update(
                    "state = ?1 WHERE uuid = ?2", 
                    workspace.getState(), 
                    workspace.getUuid()
                );
                return updateRequest.onItem().transform(i -> {
                    if(i > 0) return workspace;
                    return null;
                });
            }).onFailure().invoke(r -> log.error("error: ", r));
    }

    @Blocking(ordered = true)
    @Incoming("state-change")
    public void onMessage(String message) {
        log.info("The message: {}", message);
        UUID uuid = UUID.fromString(getWorkspaceId(message));
        findWorkspace(uuid);
    }
}
