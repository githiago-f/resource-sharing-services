package org.acme.gateway.app.graphql.entity;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Data;

enum WorkspaceState {
    IDLE, STARTING, RUNNING, STOPPING, STOPED;
    
    @JsonValue
    public String getLabel() {
        return this.name().toLowerCase();
    }
}

@Data
public class Workspace {
    private UUID uuid;
    private String image;
    private List<String> parameters; // docker parameters
    private UUID userId;
    private WorkspaceState state;

    public boolean checkOwner(String uuid) {
        return userId.equals(UUID.fromString(uuid));
    }

    @Override
    public String toString() {
        return "Workspace(uuid=" + uuid + ", userId=" + userId + ", state=" + state + ")";
    }    
}
