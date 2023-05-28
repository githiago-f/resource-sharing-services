package org.acme.hrm.domain.computational.summary;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class OperationSummary {
    private Long id;
    private String program;
    private String args;
    private String status;
    private Instant startTime;
}
