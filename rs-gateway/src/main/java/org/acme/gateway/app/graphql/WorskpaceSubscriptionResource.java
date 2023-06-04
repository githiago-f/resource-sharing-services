package org.acme.gateway.app.graphql;

import java.util.UUID;
import java.util.function.Predicate;

import org.acme.gateway.app.graphql.entity.Workspace;
import org.acme.gateway.app.graphql.processors.WorkspaceProcessor;
import org.eclipse.microprofile.graphql.GraphQLApi;

import jakarta.inject.Inject;
import io.smallrye.mutiny.Multi;
import io.smallrye.graphql.api.Subscription;
import io.vertx.core.cli.annotations.Description;

@GraphQLApi
public class WorskpaceSubscriptionResource {
    @Inject WorkspaceProcessor processor;

    @Subscription
    @Description("Subscribe to any changes for one owned Workspace")
    public Multi<Workspace> workspaceSubscription(String workspaceId) {
        UUID workspaceUuid = UUID.fromString(workspaceId);
        Predicate<Workspace> filter = workspace -> {
            return workspace.getUuid().equals(workspaceUuid);
        };
        return processor.getProcessor().filter(filter);
    }
}
