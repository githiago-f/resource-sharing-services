package org.acme.hrm.domain.executive.commands;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class StartWorkspaceCommand {
    private String image;
    private String parameters;

    public List<String> getParameters() {
        return Arrays.asList(parameters.split("\s"));
    }
}
