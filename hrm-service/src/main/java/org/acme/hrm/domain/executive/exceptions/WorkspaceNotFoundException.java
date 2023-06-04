package org.acme.hrm.domain.executive.exceptions;

import jakarta.ws.rs.NotFoundException;

public class WorkspaceNotFoundException extends NotFoundException {
    public WorkspaceNotFoundException(String workspaceId) {
        super("Workspace " + workspaceId + " not found");
    }
}
