package org.acme.gateway.app.graphql;

import java.util.List;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.acme.gateway.infra.rest.WorkspaceClient;
import org.eclipse.microprofile.graphql.Description;
import org.acme.gateway.app.graphql.entity.Workspace;
import org.acme.gateway.app.graphql.inputs.CreateWorkspaceDTO;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.acme.gateway.app.graphql.processors.WorkspaceProcessor;

import jakarta.inject.Inject;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;
import io.quarkus.security.Authenticated;

@Slf4j
@GraphQLApi
@Authenticated
@Description("A workspace is the domain feature of this application and is "+
    "related to the hability of starting a virtual environment.")
public class WorkspaceGraphQLResource {
    @Inject WorkspaceProcessor processor;
    @Inject @Claim(standard = Claims.sub) String sub;
    @Inject @Claim(standard = Claims.iss) String iss;
    @Inject @RestClient WorkspaceClient workspaceClient;

    @Query
    @Description("List all workspaces created by one user")
    public Uni<List<Workspace>> workspacesByUser(Integer page) {
        return workspaceClient.findAllByUser(page);
    }

    @Query
    @Description("Get workspace")
    public Uni<Workspace> workspace(String workspaceId) {
        return workspaceClient.findOneByUser(workspaceId);
    }

    @Mutation
    @Description("Request a quote for processing the required image")
    public Uni<Workspace> workspace(CreateWorkspaceDTO command) {
        log.info("Sending start command for user {} from {}", sub, iss);
        return workspaceClient.create(command)
            .onItem().invoke(i -> log.info(i.toString()))
            .onItem().ifNotNull().invoke(processor.getProcessor()::onNext);
    }

    @Mutation
    @Description("Start/Stop a given workspace for user")
    public Uni<Workspace> toggleWorkspaceState(String workspaceId) {
        return workspaceClient.changeState(workspaceId)
            .onItem().invoke(i -> log.info(i.toString()))
            .onItem().ifNotNull().invoke(processor.getProcessor()::onNext);
    }
}
