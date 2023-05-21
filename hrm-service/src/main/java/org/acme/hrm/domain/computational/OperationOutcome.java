package org.acme.hrm.domain.computational;

import org.acme.hrm.domain.computational.vo.OperationId;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "operations_outcome")
public class OperationOutcome {
    @Id
    private OperationId operationId;
    private String logFileUrl;
}
