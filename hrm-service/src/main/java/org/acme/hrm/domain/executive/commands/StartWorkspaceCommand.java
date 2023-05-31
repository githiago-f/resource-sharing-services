package org.acme.hrm.domain.executive.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class StartWorkspaceCommand {
    private Optional<String> uuid;
    private String image;
    private String parameters;

    public List<String> getParameters() {
        return Arrays.asList(parameters.split("\s"));
    }
}
