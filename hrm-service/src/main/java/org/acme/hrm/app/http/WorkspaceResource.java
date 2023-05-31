package org.acme.hrm.app.http;

import org.acme.hrm.domain.executive.WorkspaceService;
import org.acme.hrm.domain.executive.commands.StartWorkspaceCommand;
import org.acme.hrm.domain.executive.commands.StopWorkspaceCommand;
import org.acme.hrm.domain.executive.workspace.Workspace;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import io.quarkus.security.Authenticated;
import io.quarkus.vertx.web.Body;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RouteBase;
import io.quarkus.vertx.web.Route.HttpMethod;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;

import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;

@SecurityScheme(
    securitySchemeName = "jwt", 
    type = SecuritySchemeType.HTTP, 
    scheme = "bearer",
    bearerFormat = "jwt"
)
@RouteBase(path = "/workspaces")
@SecurityRequirement(name = "jwt")
public class WorkspaceResource {
    @Inject WorkspaceService workspaceService;

    @Authenticated
    @Route(path = "/start", methods = HttpMethod.PUT)
    @Operation(description = "Start an existing or create a new workspace for the given image")
    Uni<Workspace> start(@Body StartWorkspaceCommand command) {
        return workspaceService.requestStart(command);
    }

    @Authenticated
    @Route(path = "/stop", methods = HttpMethod.POST)
    String stop(@Body StopWorkspaceCommand command) {
        workspaceService.requestStop(command);
        return "void"; // :smile_face:
    }
}
