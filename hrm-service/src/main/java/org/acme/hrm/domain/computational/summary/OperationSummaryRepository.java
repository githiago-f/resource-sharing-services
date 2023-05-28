package org.acme.hrm.domain.computational.summary;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class OperationSummaryRepository {
    @Inject EntityManager em;

    public void save(OperationSummary summary) {
        em.getTransaction().begin();
        em.persist(summary);
        em.getTransaction().commit();
    }
}
