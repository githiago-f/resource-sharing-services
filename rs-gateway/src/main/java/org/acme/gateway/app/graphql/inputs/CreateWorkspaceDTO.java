package org.acme.gateway.app.graphql.inputs;

import lombok.Data;

@Data
public final class CreateWorkspaceDTO {
    private String image, parameters;
}
