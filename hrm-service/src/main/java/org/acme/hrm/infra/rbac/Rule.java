package org.acme.hrm.infra.rbac;

/**
 * Simple RBAC access rule
 */
public interface Rule<Subject, T> {
    /**
     * @return The object on wich the rule should be applied
     */
    Subject getSubject();
    /**
     * Checks if the rule is satisfied for the current subject
     * @param object to check
     * @return true if the object satiesfies the rule for the subject
     */
    boolean isSatisfiedBy(T object);
}
