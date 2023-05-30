package org.acme.hrm.domain.executive.commands;

import java.util.Arrays;
import java.util.UUID;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.With;

@Data @AllArgsConstructor
public class StartWorkspaceCommand {
    private String image;
    private String parameters;
    @With private UUID userId;

    public List<String> getParameters() {
        return Arrays.asList(parameters.split("\s"));
    }
}
