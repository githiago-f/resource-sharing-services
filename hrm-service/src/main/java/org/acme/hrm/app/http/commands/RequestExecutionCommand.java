package org.acme.hrm.app.http.commands;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class RequestExecutionCommand {
    private String userId;
    private String program;
    private List<String> parameters;

    public UUID getUserId() {
        return UUID.fromString(userId);
    }
}
