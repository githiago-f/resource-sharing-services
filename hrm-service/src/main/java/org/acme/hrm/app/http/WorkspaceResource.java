package org.acme.hrm.app.http;

import java.util.UUID;

import org.acme.hrm.domain.executive.WorkspaceService;
import org.acme.hrm.domain.executive.commands.StartWorkspaceCommand;
import org.acme.hrm.domain.executive.commands.StopWorkspaceCommand;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/workspace")
public class WorkspaceResource {

    @Inject @Claim(standard = Claims.sub) String sub;
    @Inject WorkspaceService workspaceService;

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response start(StartWorkspaceCommand command) {
        workspaceService.requestStart(command.withUserId(UUID.fromString(sub)));
        return Response.ok().build();
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response stop(StopWorkspaceCommand command) {
        workspaceService.requestStop(command);
        return Response.ok().build();
    }
}
