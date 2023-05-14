package org.acme.hrm.domain.computational.vo;

public enum OperationState {
    WAITING("waiting"), 
    EXECUTING("executing"), 
    FINISHED("finished"), 
    ERROR("error");

    private final String label;
    private OperationState(String label) {
        this.label = label;
    }


    public String getLabel() {
        return label;
    }
}
