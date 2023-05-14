package org.acme.hrm.infra.interceptors;

import java.util.UUID;

import io.quarkus.agroal.DataSource;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@DataSource("querydb")
public class OperationView {
    @Id
    private UUID id;
}
