package org.acme.hrm.domain.computational;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.acme.hrm.app.http.commands.RequestExecutionCommand;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
public class OperationsService {
    @Inject OperationsRepository operationsRepository;

    @Transactional
    public Operation requestExecution(RequestExecutionCommand command) {
        Operation operation = Operation.builder()
            .program(command.getProgram())
            .parameters(command.getParameters())
            .build();
        operationsRepository.persistAndFlush(operation);
        log.info("Created Operation::{}", operation.getIdentity().getUuid());
        return operation;
    }
}
