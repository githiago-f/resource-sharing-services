package org.acme.hrm.domain.computational.mappers;

import java.util.function.Function;

import org.acme.hrm.domain.computational.Operation;

/**
 * Create a new operation from persisted operation
 * updating its version
 */
public class OperationUpdaterMapper implements Function<Operation, Operation> {
    @Override
    public Operation apply(Operation oldOperation) {
        var newOperation = new Operation();
        newOperation.setArgs(oldOperation.getArgs());
        newOperation.setIdentity(oldOperation.getIdentity());
        newOperation.setProgram(oldOperation.getProgram());
        newOperation.setState(oldOperation.getState());
        newOperation.setUserId(oldOperation.getUserId());
        newOperation.updateVersion();
        return newOperation;
    }

    public static Function<Operation, Operation> appliable() {
        return new OperationUpdaterMapper()::apply;
    }
}
