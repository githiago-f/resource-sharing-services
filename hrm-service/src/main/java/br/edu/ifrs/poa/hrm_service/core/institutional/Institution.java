package br.edu.ifrs.poa.hrm_service.core.institutional;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class Institution {
    private final String name;

    private Set<Resource> resources;

    public void addResource(Resource resource) {
        if(resources == null) {
            resources = new HashSet<>();
        }
        this.resources.add(resource);
    }
}
