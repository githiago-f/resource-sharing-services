package org.acme.gateway.infra.rest;

import org.acme.gateway.app.graphql.entity.Workspace;
import org.acme.gateway.app.graphql.inputs.StartWorkspace;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/workspaces")
@RegisterRestClient(configKey = "workspaces")
public interface WorkspaceClient {
    @POST
    Uni<Workspace> start(StartWorkspace command);
}
