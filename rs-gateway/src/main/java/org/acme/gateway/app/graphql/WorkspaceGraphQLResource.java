package org.acme.gateway.app.graphql;

import org.acme.gateway.app.graphql.entity.Workspace;
import org.acme.gateway.app.graphql.inputs.StartWorkspace;
import org.acme.gateway.infra.rest.WorkspaceClient;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@GraphQLApi
@Authenticated
@Description("A workspace is the domain feature of this application and is "+
    "related to the hability of starting a virtual environment")
public class WorkspaceGraphQLResource {

    @Inject JsonWebToken jwt;
    @Inject @RestClient WorkspaceClient workspaceClient;

    @Query
    @Description("List all workspaces created by one user")
    public Uni<Workspace> listWorkspacesByUser(String userId) {
        return null;
    }

    @Mutation(value = "startMachine")
    @Description("Request a quote for processing the required image")
    public Uni<Workspace> startMachine(StartWorkspace command) {
        log.info("Sending start command for user {} from {}", jwt.getSubject(), jwt.getIssuer());
        return workspaceClient.start(command);
    }
}
