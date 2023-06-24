package org.acme.gateway.infra.rest;

import java.util.List;

import org.acme.gateway.app.graphql.entity.Workspace;
import org.acme.gateway.app.graphql.inputs.CreateWorkspaceDTO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.*;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.MediaType;
import io.quarkus.oidc.token.propagation.AccessToken;

@AccessToken
@Path("/workspaces")
@RegisterRestClient(configKey = "workspaces")
public interface WorkspaceClient {
    @GET
    @Path("/list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Uni<List<Workspace>> findAllByUser(@QueryParam("page") Integer page);

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Uni<Workspace> create(CreateWorkspaceDTO command);

    @GET
    @Path("/{uuid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Uni<Workspace> findOneByUser(@PathParam("uuid") String uuid);

    @PUT
    @Path("/{uuid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Uni<Workspace> changeState(@PathParam("uuid") String uuid);
}
