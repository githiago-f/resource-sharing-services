package org.acme.hrm.domain.computational;

import java.util.Optional;
import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OperationsRepository implements PanacheRepository<Operation> {
    public Optional<Operation> findLastVersion(UUID id) {
        var query = getEntityManager().createQuery(
            "FROM Operation o " +
            "WHERE o.identity.uuid = :id " +
            "ORDER BY o.identity.offset DESC LIMIT 1", 
            Operation.class
        );
        query.setParameter("id", id);
        return Optional.ofNullable(query.getSingleResult());
    }
}
