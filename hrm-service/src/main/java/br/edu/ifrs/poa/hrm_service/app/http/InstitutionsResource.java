package br.edu.ifrs.poa.hrm_service.app.http;

import br.edu.ifrs.poa.hrm_service.core.institutional.Institution;
import br.edu.ifrs.poa.hrm_service.core.institutional.Resource;
import br.edu.ifrs.poa.hrm_service.core.institutional.vo.ResourceStatus;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

@Path("/institutions")
public class InstitutionsResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Institution> index() {
        Resource resource = Resource.builder()
                .memoryCapacity(32768)
                .uuid(UUID.randomUUID())
                .status(ResourceStatus.ONLINE)
                .build();
        Function<String, Institution> institution = (String name) -> Institution.builder().name(name).build();

        Institution ifrs = institution.apply("IFRS");
        ifrs.addResource(resource);

        return Set.of(
            ifrs,
            institution.apply("IFMG"),
            institution.apply("IFSC"),
            institution.apply("IFSE")
        );
    }
}
