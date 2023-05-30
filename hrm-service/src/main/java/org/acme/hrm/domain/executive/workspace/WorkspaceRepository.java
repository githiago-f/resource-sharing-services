package org.acme.hrm.domain.executive.workspace;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WorkspaceRepository implements PanacheRepository<Workspace> {}
