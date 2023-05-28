package org.acme.hrm.domain.computational;

import java.time.Instant;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * persists the current processed offset for different types
 * of event handlers
 */
@Entity 
@Getter @Setter
@NoArgsConstructor
@Table(
    name = "event_offset", 
    indexes = { @Index(columnList = "eventType") }
)
public class Offset extends PanacheEntity {
    private String eventType;
    private Instant offset;
}
