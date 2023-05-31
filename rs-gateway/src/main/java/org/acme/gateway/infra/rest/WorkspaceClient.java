package org.acme.gateway.infra.rest;

import org.acme.gateway.app.graphql.entity.Workspace;
import org.acme.gateway.app.graphql.inputs.StartWorkspace;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.quarkus.oidc.token.propagation.AccessToken;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

@AccessToken
@Path("/workspaces")
@RegisterRestClient(configKey = "workspaces")
public interface WorkspaceClient {
    @POST
    @Path("/start")
    @Consumes(MediaType.APPLICATION_JSON)
    Uni<Workspace> start(StartWorkspace command);
}
