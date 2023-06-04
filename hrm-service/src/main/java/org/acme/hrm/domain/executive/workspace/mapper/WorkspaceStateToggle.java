package org.acme.hrm.domain.executive.workspace.mapper;

import java.util.function.Function;

import org.acme.hrm.domain.executive.workspace.Workspace;
import org.acme.hrm.domain.executive.workspace.vo.WorkspaceState;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WorkspaceStateToggle implements Function<Workspace, Workspace> {

    @Override
    public Workspace apply(Workspace workspace) {
        log.info("workspace state:::{}", workspace.getState());
        switch(workspace.getState()) {
            case STOPPING, STOPED:
                return workspace.withState(WorkspaceState.STARTING);
            case STARTING, RUNNING, IDLE:
                return workspace.withState(WorkspaceState.STOPPING);
            default: return null;
        }
    }
}
