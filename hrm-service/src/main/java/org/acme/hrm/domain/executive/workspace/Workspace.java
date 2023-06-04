package org.acme.hrm.domain.executive.workspace;

import java.util.List;
import java.util.UUID;

import org.acme.hrm.infra.rbac.CheckOwnerId;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.acme.hrm.infra.converters.ArrayListConverter;
import org.acme.hrm.domain.executive.workspace.vo.WorkspaceState;

import lombok.*;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Basic;
import jakarta.persistence.Convert;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Builder @Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "workspaces", indexes = @Index(columnList = "userId"))
public class Workspace implements CheckOwnerId {
    @Id
    @Basic
    @JdbcTypeCode(SqlTypes.CHAR)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    private String image;
    
    @Convert(converter = ArrayListConverter.class)
    private List<String> parameters; // docker parameters
    
    private UUID userId;
    
    @Builder.Default
    @With private WorkspaceState state = WorkspaceState.STOPED;
    
    @Override
    public String toString() {
        return "Workspace(uuid=" + uuid + ", state=" + state + ", userId=" + userId + ")";
    }
}
