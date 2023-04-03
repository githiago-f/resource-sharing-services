package br.edu.ifrs.poa.hrm_service.core.institutional;

import br.edu.ifrs.poa.hrm_service.core.institutional.vo.ResourceStatus;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Resource {
    private final UUID uuid;
    private ResourceStatus status;
    /**
     * @apiNote Format is MiB
     */
    private Integer memoryCapacity;
}
