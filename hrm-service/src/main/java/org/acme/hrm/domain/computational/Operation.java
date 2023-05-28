package org.acme.hrm.domain.computational;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;

import org.acme.hrm.domain.computational.vo.OperationId;
import org.acme.hrm.domain.computational.vo.OperationState;

@Entity 
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "operations")
public class Operation {
    @EmbeddedId
    @Builder.Default
    private OperationId identity = new OperationId();
    private String program;
    private List<String> parameters;
    
    @Builder.Default
    @With private OperationState state = OperationState.WAITING;

    public void updateVersion() {
        this.identity.setOffset(Instant.now());
    }
}

