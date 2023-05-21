package org.acme.hrm.app.http;

import org.acme.hrm.app.http.commands.RequestExecutionCommand;
import org.acme.hrm.app.http.commands.UpdateExecutionStateCommand;
import org.acme.hrm.domain.computational.Operation;
import org.acme.hrm.domain.computational.OperationsService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/operations")
public class OperationsResource {
    @Inject OperationsService operationsService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Operation requestExecution(RequestExecutionCommand command) {
        return operationsService.requestExecution(command);
    }

    @PUT
    @Path("{operationId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response change(
        @PathParam("operationId") String operationId, 
        UpdateExecutionStateCommand command
    ) {
        command.setId(operationId);
        return operationsService.persistNewState(command)
            .map(operation -> Response.ok(operation).build())
            .orElse(Response.status(Status.NOT_FOUND).build());
    }
}
