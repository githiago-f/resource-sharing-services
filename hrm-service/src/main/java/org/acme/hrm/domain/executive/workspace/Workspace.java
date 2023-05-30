package org.acme.hrm.domain.executive.workspace;

import java.util.List;
import java.util.UUID;

import org.acme.hrm.domain.executive.workspace.vo.WorkspaceState;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Builder @Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "workspaces")
public class Workspace {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    private String image;
    private List<String> parameters; // docker parameters
    private UUID userId;
    @Builder.Default
    @With private WorkspaceState state = WorkspaceState.STOPED;
}
