package org.acme.gateway.app.graphql.inputs;

import lombok.Data;

@Data
public final class StartWorkspace {
    private String image, parameters;
}
