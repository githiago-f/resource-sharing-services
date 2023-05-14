package org.acme.hrm.domain.computational;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.acme.hrm.domain.computational.vo.OperationId;
import org.acme.hrm.domain.computational.vo.OperationState;

@Entity @Getter @Setter
public class Operation {
    @EmbeddedId
    private OperationId identity;

    private String program; // echo
    private List<String> args; // { "Hello World!" }

    @Column(nullable = false)
    private UUID userId;
    private OperationState state;

    private Instant updatedAt;
    
    public Operation() {
        identity = new OperationId();
        userId = null;
        state = OperationState.WAITING;
        updatedAt = null;
    }

    public Operation(String userId) {
        this();
        this.userId = UUID.fromString(userId);
    }

    public Operation(String id, String userId) {
        this(userId);
        this.identity.setUuid(UUID.fromString(id));
    }

    public void updateVersion() {
        updatedAt = Instant.now();
        this.identity.setOffset(updatedAt);
    }
}

