package org.acme.hrm.app.http;

import java.util.List;

import org.acme.hrm.domain.executive.WorkspaceService;
import org.acme.hrm.domain.executive.workspace.Workspace;
import org.acme.hrm.domain.executive.commands.CreateWorkspaceCommand;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import jakarta.inject.Inject;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;
import io.quarkus.vertx.web.Body;
import io.quarkus.vertx.web.Param;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RouteBase;
import io.quarkus.panache.common.Page;
import io.quarkus.security.Authenticated;
import io.quarkus.vertx.web.Route.HttpMethod;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;

@Slf4j
@Authenticated
@SecurityScheme(
    securitySchemeName = "jwt", 
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "jwt"
)
@RouteBase(path = "/workspaces")
@SecurityRequirement(name = "jwt")
public class WorkspaceResource {
    final Integer PER_PAGE = 10;

    @Inject WorkspaceService workspaceService;

    @Route(path = "/list", methods = HttpMethod.GET)
    @Operation(summary = "Get user's workspaces")
    Uni<List<Workspace>> findAllByUser(@Param("page") Integer page) {
        Page currentPage = Page.of(page * PER_PAGE, PER_PAGE);
        return workspaceService.getAllUserWorkspaces(currentPage);
    }

    @Route(path = "/create", methods = HttpMethod.POST)
    @Operation(summary = "Create workspace")
    Uni<Workspace> create(@Body CreateWorkspaceCommand command) {
        log.info("Request workspace for image {}", command.getImage());
        return workspaceService.requestWorkspace(command);
    }

    @Route(path = "/:uuid", methods = HttpMethod.GET)
    @Operation(summary = "Get one workspace")
    Uni<Workspace> findOneByUser(@Param("uuid") String uuid) {
        return workspaceService.getWorkspace(uuid);
    }

    @Operation(summary = "Change workspace's state")
    @Route(path = "/:uuid", methods = HttpMethod.PUT, order = 2)
    Uni<Workspace> changeState(@Param("uuid") String uuid) {
        log.info("Request workspace {} toggle state", uuid);
        return workspaceService.requestChangeState(uuid);
    }
}
