package org.acme.hrm.domain.computational;

import java.util.UUID;

import org.acme.hrm.domain.computational.vo.SessionState;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity 
@Builder @Data
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    @Id private UUID userId;
    
    @With
    @Builder.Default
    private SessionState state = SessionState.STOPPED;

    private String image;
}
