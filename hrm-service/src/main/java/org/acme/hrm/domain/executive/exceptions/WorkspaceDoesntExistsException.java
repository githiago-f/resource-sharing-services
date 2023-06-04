package org.acme.hrm.domain.executive.exceptions;

import io.quarkus.security.UnauthorizedException;

public class WorkspaceDoesntExistsException extends UnauthorizedException {
    public WorkspaceDoesntExistsException(String sub) {
        super("This workspace doesn't exist for user " + sub);
    }
}
