package org.acme.hrm.app.http;

import org.acme.hrm.app.http.commands.RequestExecutionCommand;
import org.acme.hrm.domain.computational.OperationsService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/operations")
public class OperationsResource {
    @Inject OperationsService operationsService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response requestExecution(RequestExecutionCommand command) {
        var operation = operationsService.requestExecution(command);
        return Response.ok(operation).build();
    }
}
