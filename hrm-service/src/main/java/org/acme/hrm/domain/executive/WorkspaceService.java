package org.acme.hrm.domain.executive;

import java.util.List;
import java.util.UUID;

import org.acme.hrm.infra.rbac.OwnRule;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.acme.hrm.domain.executive.workspace.Workspace;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.OnOverflow;
import org.acme.hrm.domain.executive.workspace.vo.WorkspaceState;
import org.acme.hrm.domain.executive.workspace.WorkspaceRepository;
import org.acme.hrm.domain.executive.workspace.mapper.WorkspaceStateToggle;
import org.acme.hrm.domain.executive.commands.CreateWorkspaceCommand;
import org.eclipse.microprofile.reactive.messaging.OnOverflow.Strategy;
import org.acme.hrm.domain.executive.exceptions.WorkspaceDoesntExistsException;
import org.acme.hrm.domain.executive.exceptions.WorkspaceNotFoundException;

import jakarta.inject.Inject;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.reactive.panache.common.WithSession;

@Slf4j
@WithSession
@ApplicationScoped
public class WorkspaceService {
    @Inject @Claim(standard = Claims.sub) String sub;

    @OnOverflow(value = Strategy.THROW_EXCEPTION)
    @Channel("workspace-state-change") Emitter<String> stateEmmiter;

    @Inject WorkspaceRepository workspaceRepository;

    private String message(Workspace workspace) {
        return workspace.getUuid().toString() + ":" + workspace.getState();
    }

    private Workspace getWorkspaceIfOwner(Workspace workspace) {
        if(new OwnRule<>(workspace).isSatisfiedBy(sub)) {
            return workspace;
        }
        return null;
    }

    public Uni<Workspace> getWorkspace(String workspaceId) {
        return workspaceRepository.findById(UUID.fromString(workspaceId))
            .onItem().ifNotNull().transform(this::getWorkspaceIfOwner)
            .onItem().ifNull().failWith(new WorkspaceDoesntExistsException(sub));
    }

    public Uni<List<Workspace>> getAllUserWorkspaces(Page page) {
        UUID userId = UUID.fromString(sub);
        return workspaceRepository.findByUserIdPaged(userId, page);
    }

    public Uni<Workspace> requestWorkspace(CreateWorkspaceCommand command) {
        Workspace workspace = Workspace.builder()
                .userId(UUID.fromString(sub))
                .image(command.getImage())
                .parameters(command.getParameters())
                .state(WorkspaceState.STARTING)
                .build();
        
        return workspaceRepository.persistAndFlush(workspace)
            .onItem().invoke(ws -> stateEmmiter.send(message(ws)));
    }

    public Uni<Workspace> requestChangeState(String uuid) {
        return workspaceRepository.findForUpdate(UUID.fromString(uuid))
            .onItem().ifNull().failWith(new WorkspaceNotFoundException(uuid))
            .onItem().ifNotNull().transform(this::getWorkspaceIfOwner)
            .onItem().ifNotNull().transform(new WorkspaceStateToggle()::apply)
            .onItem().ifNotNull().call(workspace -> {
                var updateRequest = workspaceRepository.update(
                    "state = ?1 WHERE uuid = ?2", 
                    workspace.getState(), 
                    workspace.getUuid()
                );
                return updateRequest.onItem().transform(i -> {
                    if(i > 0) return workspace;
                    return null;
                })
                .onItem().ifNotNull().invoke(wk -> stateEmmiter.send(message(wk)))
                .onFailure().invoke(r -> log.error("error: ", r));
            });
    }
}
