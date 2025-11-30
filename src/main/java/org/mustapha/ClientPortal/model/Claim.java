package org.mustapha.ClientPortal.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.mustapha.ClientPortal.enums.ClaimStatus;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "claims")
public class Claim extends BaseEntity{

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private ClaimStatus status;

    private String attachmentUrl; // For PDF/Image file path

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    // Operator or Supervisor handling this claim
    @ManyToOne
    @JoinColumn(name = "assigned_staff_id")
    private Staff assignedStaff;
}