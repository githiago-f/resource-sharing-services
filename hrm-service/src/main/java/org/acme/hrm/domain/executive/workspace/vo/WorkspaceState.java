package org.acme.hrm.domain.executive.workspace.vo;

import com.fasterxml.jackson.annotation.JsonValue;

public enum WorkspaceState {
    IDLE, STARTING, RUNNING, STOPPING, STOPED;

    @JsonValue
    public String getLabel() {
        return this.name().toLowerCase();
    }
}
