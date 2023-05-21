package org.acme.hrm.domain.computational.summary;

import io.quarkus.agroal.DataSource;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

public class OperationSummaryRepository {
    @Inject @DataSource("querydb") EntityManager em;

    public void save(OperationSummary summary) {
        em.getTransaction().begin();
        em.persist(summary);
        em.getTransaction().commit();
    }
}
