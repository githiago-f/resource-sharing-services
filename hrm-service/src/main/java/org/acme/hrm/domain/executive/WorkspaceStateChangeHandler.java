package org.acme.hrm.domain.executive;

import java.util.UUID;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.acme.hrm.domain.executive.workspace.WorkspaceRepository;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import io.smallrye.common.annotation.Blocking;

@Slf4j
@Singleton
public class WorkspaceStateChangeHandler {
    @Inject WorkspaceRepository workspaceRepository;

    @Blocking
    @Incoming("workspace-state-change")
    public void onMessage(String message) {
        log.info("The message: {}", message);
    }
}
