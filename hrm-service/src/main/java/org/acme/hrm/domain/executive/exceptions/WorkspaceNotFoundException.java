package org.acme.hrm.domain.executive.exceptions;

import java.util.UUID;

import jakarta.ws.rs.NotFoundException;
import lombok.Getter;

@Getter
public class WorkspaceNotFoundException extends NotFoundException {
    private String workspaceId;
    public WorkspaceNotFoundException(UUID workspaceId) {
        super("Workspace not found");
        this.workspaceId = workspaceId.toString();
    }
}
