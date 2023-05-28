package org.acme.hrm.domain.computational.summary;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.acme.hrm.domain.computational.Offset;
import org.acme.hrm.domain.computational.Operation;
import org.acme.hrm.domain.computational.OperationsRepository;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class SummaryHandler {
    @Inject OperationsRepository operationsRepository;

    @Scheduled(every = "30s")
    void handle() {
        Offset offset = Offset.find("eventType", "summary_handler").firstResult();
        log.info("Offset -> {}", offset.getOffset());
        operationsRepository.findByOffset(offset.getOffset()).peek(op -> {
            log.info("Operation::{} is being processed", op.getIdentity().getOffset());
            OperationSummary summary = OperationSummary.builder()
                .program(op.getProgram())
                .args(op.getParameters().toString())
                .startTime(op.getIdentity().getOffset())
                .status(op.getState().getLabel())
                .build();
            
        });
    }
}
