package org.acme.hrm.app.http.commands;

import java.util.List;

import lombok.Data;

@Data
public class RequestExecutionCommand {
    private String program;
    private String userId;
    private List<String> args;
}
