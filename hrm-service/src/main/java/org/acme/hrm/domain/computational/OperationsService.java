package org.acme.hrm.domain.computational;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;

import org.acme.hrm.app.http.commands.RequestExecutionCommand;
import org.acme.hrm.domain.computational.mappers.OperationUpdaterMapper;
import org.acme.hrm.domain.computational.vo.OperationState;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
public class OperationsService {
    @Inject OperationsRepository operationsRepository;

    @Transactional
    public Operation requestExecution(RequestExecutionCommand command) {
        var operation = new Operation(command.getUserId());
        operation.setProgram(command.getProgram());
        operation.setArgs(command.getArgs());
        operationsRepository.persistAndFlush(operation);
        return operation;
    }

    @Transactional
    public Optional<Operation> persistNewState(OperationState operationState, String id) {
        log.info("Operation::{}", id);
        return operationsRepository.findById(UUID.fromString(id))
            .stream()
            .map(OperationUpdaterMapper.appliable())
            .peek(operation -> operation.setState(operationState))
            .peek(operationsRepository::persistAndFlush)
            .findFirst();
    }
}
