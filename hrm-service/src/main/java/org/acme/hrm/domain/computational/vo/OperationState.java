package org.acme.hrm.domain.computational.vo;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OperationState {
    WAITING("waiting", 0), 
    EXECUTING("executing", 1), 
    FINISHED("finished", 2), 
    IDLE("idle", 3),
    ERROR("error", 4);

    private final String label;
    private final Integer level;
    private OperationState(String label, Integer level) {
        this.label = label;
        this.level = level;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
    public Integer getLevel() {
        return level;
    }
}
