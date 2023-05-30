package org.acme.hrm.app.http;

import org.acme.hrm.domain.executive.WorkspaceService;
import org.acme.hrm.domain.executive.commands.StartWorkspaceCommand;
import org.acme.hrm.domain.executive.commands.StopWorkspaceCommand;
import org.acme.hrm.domain.executive.workspace.Workspace;

import io.quarkus.security.Authenticated;
import io.quarkus.vertx.web.Body;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RouteBase;
import io.quarkus.vertx.web.Route.HttpMethod;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@RouteBase(path = "/workspaces")
public class WorkspaceResource {
    @Inject WorkspaceService workspaceService;

    @Authenticated
    @Route(path = "/start", methods = HttpMethod.POST)
    Uni<Workspace> start(@Body StartWorkspaceCommand command) {
        return workspaceService.requestStart(command);
    }

    @Authenticated
    @Route(path = "/stop", methods = HttpMethod.POST)
    Response stop(@Body StopWorkspaceCommand command) {
        workspaceService.requestStop(command);
        return Response.ok().build();
    }
}
