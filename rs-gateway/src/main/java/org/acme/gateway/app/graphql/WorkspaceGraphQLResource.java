package org.acme.gateway.app.graphql;

import org.acme.gateway.app.graphql.entity.Workspace;
import org.acme.gateway.app.graphql.inputs.StartWorkspace;
import org.acme.gateway.infra.rest.WorkspaceClient;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;

@GraphQLApi
public class WorkspaceGraphQLResource {
    @Inject @RestClient WorkspaceClient workspaceClient;

    @Mutation(value = "startMachine")
    @Description("Request a quote for processing the required image")
    public Uni<Workspace> startMachine(StartWorkspace command) {
        return workspaceClient.start(command);
    }
}
