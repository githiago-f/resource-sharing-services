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
import jakarta.ws.rs.core.Response;
import io.quarkus.vertx.web.RouteBase;
import io.quarkus.panache.common.Page;
import jakarta.ws.rs.NotFoundException;
import io.quarkus.security.Authenticated;
import jakarta.ws.rs.core.Response.Status;
import io.quarkus.vertx.web.Route.HttpMethod;
import io.quarkus.security.UnauthorizedException;
import jakarta.ws.rs.core.Response.ResponseBuilder;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;
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

    @ServerExceptionMapper
    public Response map(RuntimeException e) {
        ResponseBuilder builder;
        if(e instanceof NotFoundException) {
            builder = Response.status(Status.NOT_FOUND);
        } else if(e instanceof UnauthorizedException) {
            builder = Response.status(Status.UNAUTHORIZED);
        } else {
            builder = Response.serverError();
        }
        return builder.entity(e.getMessage()).build();
    }
}
