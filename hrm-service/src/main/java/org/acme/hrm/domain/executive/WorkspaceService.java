package org.acme.hrm.domain.executive;

import java.util.UUID;

import org.acme.hrm.domain.executive.commands.StartWorkspaceCommand;
import org.acme.hrm.domain.executive.commands.StopWorkspaceCommand;
import org.acme.hrm.domain.executive.workspace.Workspace;
import org.acme.hrm.domain.executive.workspace.WorkspaceRepository;
import org.acme.hrm.domain.executive.workspace.vo.WorkspaceState;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.OnOverflow;
import org.eclipse.microprofile.reactive.messaging.OnOverflow.Strategy;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class WorkspaceService {
    @Inject @Claim(standard = Claims.sub) String sub;

    @OnOverflow(value = Strategy.THROW_EXCEPTION)
    @Channel("workspace-start") Emitter<String> startEmitter;

    @Inject WorkspaceRepository workspaceRepository;

    @WithSession @Transactional
    public Uni<Workspace> requestStart(StartWorkspaceCommand command) {
        log.info("Workspace for user {} is starting", sub);
        Workspace.WorkspaceBuilder wsBuilder = Workspace.builder()
            .userId(UUID.fromString(sub))
            .image(command.getImage())
            .parameters(command.getParameters())
            .state(WorkspaceState.STARTING);
        
        if(command.getUuid().isEmpty()) {
            return workspaceRepository.persistAndFlush(wsBuilder.build())
                .onItem().invoke(ws -> startEmitter.send(ws.getUuid().toString()));
        }

        String uuid = command.getUuid().get();
        log.info("Workspace id provided: {}", uuid);
        wsBuilder.uuid(UUID.fromString(uuid));
        
        return workspaceRepository.persist(wsBuilder.build());
    }

    public void requestExecute() {}
    
    public void requestStop(StopWorkspaceCommand command) {}
}
