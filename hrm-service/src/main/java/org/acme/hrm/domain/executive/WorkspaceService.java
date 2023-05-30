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

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class WorkspaceService {
    @Inject @Claim(standard = Claims.sub) String sub;

    @OnOverflow(value = Strategy.THROW_EXCEPTION)
    @Channel("workspace-start") Emitter<Workspace> startEmitter;

    @Inject WorkspaceRepository workspaceRepository;

    public Uni<Workspace> requestStart(StartWorkspaceCommand command) {
        Workspace workspace = Workspace.builder()
            .userId(UUID.fromString(sub))
            .image(command.getImage())
            .parameters(command.getParameters())
            .state(WorkspaceState.STARTING)
            .build();
        return workspaceRepository.persistAndFlush(workspace)
            .onItem()
            .invoke(startEmitter::send);
    }

    public void requestExecute() {}
    
    public void requestStop(StopWorkspaceCommand command) {}
}
