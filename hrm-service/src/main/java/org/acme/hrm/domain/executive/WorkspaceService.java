package org.acme.hrm.domain.executive;

import org.acme.hrm.domain.executive.commands.StartWorkspaceCommand;
import org.acme.hrm.domain.executive.commands.StopWorkspaceCommand;
import org.acme.hrm.domain.executive.workspace.Workspace;
import org.acme.hrm.domain.executive.workspace.WorkspaceRepository;
import org.acme.hrm.domain.executive.workspace.vo.WorkspaceState;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.OnOverflow;
import org.eclipse.microprofile.reactive.messaging.OnOverflow.Strategy;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class WorkspaceService {
    @OnOverflow(value = Strategy.THROW_EXCEPTION)
    @Channel("workspace-start") Emitter<Workspace> startEmitter;

    @Inject WorkspaceRepository workspaceRepository;

    public Workspace requestStart(StartWorkspaceCommand command) {
        Workspace workspace = Workspace.builder()
            .image(command.getImage())
            .parameters(command.getParameters())
            .state(WorkspaceState.STARTING)
            .build();
        workspaceRepository.persistAndFlush(workspace)
            .onItem()
            .invoke(startEmitter::send);
        return workspace;
    }

    public void requestExecute() {}
    
    public void requestStop(StopWorkspaceCommand command) {}
}
