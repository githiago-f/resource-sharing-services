package org.acme.hrm.domain.computational.vo;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OperationState {
    WAITING("waiting"),     // 0
    EXECUTING("executing"), // 1
    FINISHED("finished"),   // 2
    ERROR("error");         // 3

    private final String label;
    private OperationState(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
