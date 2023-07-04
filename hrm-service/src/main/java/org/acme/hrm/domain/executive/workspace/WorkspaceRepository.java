package org.acme.hrm.domain.executive.workspace;

import java.util.List;
import java.util.UUID;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.LockModeType;

@ApplicationScoped
public class WorkspaceRepository implements PanacheRepositoryBase<Workspace, UUID> {
    public Uni<List<Workspace>> findByUserIdPaged(UUID userId, Page page) {
        return find("userId", userId).page(page).list();
    }

    public Uni<Workspace> findForUpdate(UUID uuid) {
        return findById(uuid, LockModeType.PESSIMISTIC_WRITE);
    }
}
