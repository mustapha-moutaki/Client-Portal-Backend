package org.mustaha.ClientPortal.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.mustaha.ClientPortal.enums.LeadStatus;


@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "leads")
public class Lead extends BaseEntity {

    private String name;
    private String contactInfo; // Email or Phone
    private String source;

    @Enumerated(EnumType.STRING)
    private LeadStatus status;

    @ManyToOne
    @JoinColumn(name = "assigned_operator_id")
    private Staff assignedOperator;

    @Column(columnDefinition = "TEXT")
    private String notes;
}