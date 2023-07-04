package org.acme.gateway.app.graphql.processors;

import org.acme.gateway.app.graphql.entity.Workspace;

import jakarta.enterprise.context.ApplicationScoped;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;

@ApplicationScoped
public class WorkspaceProcessor {
    private final BroadcastProcessor<Workspace> processor;

    public WorkspaceProcessor() {
        processor = BroadcastProcessor.create();
    }

    public BroadcastProcessor<Workspace> getProcessor() {
        return processor;
    }
}
