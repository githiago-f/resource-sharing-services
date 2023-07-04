package org.acme.hrm.infra.rbac;

import java.util.UUID;

public interface CheckOwnerId {
    UUID getUserId();
    default boolean check(UUID ownerId) {
        return getUserId().equals(ownerId);
    }
}
