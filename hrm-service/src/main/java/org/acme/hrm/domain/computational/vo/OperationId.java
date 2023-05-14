package org.acme.hrm.domain.computational.vo;

import java.time.Instant;
import java.util.UUID;

import lombok.Data;
import jakarta.persistence.Embeddable;

@Data
@Embeddable
public class OperationId {
    private UUID uuid;
    private Instant offset;

    public OperationId() {
        uuid = UUID.randomUUID();
        offset = Instant.now();
    }
}
