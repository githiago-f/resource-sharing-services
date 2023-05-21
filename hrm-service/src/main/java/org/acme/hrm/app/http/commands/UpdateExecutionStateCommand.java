package org.acme.hrm.app.http.commands;

import org.acme.hrm.domain.computational.vo.OperationState;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class UpdateExecutionStateCommand {
    private String id;
    private OperationState operationState;
    @Nullable private String outcomeLogFileUrl;
}
