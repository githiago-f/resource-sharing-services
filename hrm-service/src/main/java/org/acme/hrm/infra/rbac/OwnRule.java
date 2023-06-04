package org.acme.hrm.infra.rbac;

import java.util.UUID;

import lombok.Getter;
import lombok.AllArgsConstructor;

@AllArgsConstructor @Getter
public class OwnRule<Sub extends CheckOwnerId> implements Rule<Sub, String> {
    private Sub subject;

    @Override
    public boolean isSatisfiedBy(String uuid) {
        if(getSubject() == null) return false;
        if(getSubject().check(UUID.fromString(uuid))) return true;
        return false;
    }
}
