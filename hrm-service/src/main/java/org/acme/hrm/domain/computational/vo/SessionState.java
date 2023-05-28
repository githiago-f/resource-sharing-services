package org.acme.hrm.domain.computational.vo;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SessionState {
    IDLE("idle"), RUNNING("running"), STOPPED("stopped");

    private final String label;
    SessionState(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
