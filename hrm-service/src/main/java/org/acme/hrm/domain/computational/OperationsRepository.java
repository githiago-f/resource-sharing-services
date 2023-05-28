package org.acme.hrm.domain.computational;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OperationsRepository implements PanacheRepository<Operation> {
    public Optional<Operation> findLastVersion(UUID id) {
        var query = getEntityManager().createQuery(
            "FROM Operation o " +
            "WHERE o.identity.uuid = :id " +
            "ORDER BY o.identity.offset DESC", 
            Operation.class
        );
        query.setParameter("id", id);
        return Optional.ofNullable(query.getSingleResult());
    }

    public Stream<Operation> findByOffset(Instant offset) {
        var query = getEntityManager().createQuery(
            "FROM Operation o " +
            "WHERE o.identity.offset >= TIMESTAMP(:offset) ", 
            Operation.class
        );
        query.setParameter("offset", offset);
        return query.setMaxResults(10).getResultStream(); 
    }
}
