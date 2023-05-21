package org.acme.hrm.domain.computational;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;

import org.acme.hrm.app.http.commands.RequestExecutionCommand;
import org.acme.hrm.app.http.commands.UpdateExecutionStateCommand;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
public class OperationsService {
    @Inject OperationsRepository operationsRepository;

    @Transactional
    public Operation requestExecution(RequestExecutionCommand command) {
        Operation operation = Operation.builder()
            .userId(command.getUserId()) 
            .imageName(command.getImageName())
            .parameters(command.getParameters())
            .build();
        operationsRepository.persistAndFlush(operation);
        log.info("Created Operation::{}", operation.getIdentity().getUuid());
        return operation;
    }

    @Transactional
    public Optional<Operation> persistNewState(UpdateExecutionStateCommand command) {
        log.info("Changed Operation::{}", command.getId());
        UUID id = UUID.fromString(command.getId());
        return operationsRepository.findLastVersion(id)
            .stream()
            .map(operation -> {
                OperationOutcome outcome = OperationOutcome.builder()
                    .operationId(operation.getIdentity())
                    .logFileUrl(command.getOutcomeLogFileUrl())
                    .build();
                Operation newOperation = operation.withOutcome(outcome)
                    .withState(command.getOperationState());
                newOperation.updateVersion();
                return newOperation;
            })
            .peek(operationsRepository::persistAndFlush)
            .findFirst();
    }
}
