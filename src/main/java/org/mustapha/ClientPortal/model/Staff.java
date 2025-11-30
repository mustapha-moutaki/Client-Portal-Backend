package org.mustapha.ClientPortal.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;


/**
 * don't repeat urself
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "staff")
@PrimaryKeyJoinColumn(name = "user_id")
// staff (Operator, Supervisor, Admin)
public class Staff extends User {

    private String firstName;
    private String lastName;


    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private Staff supervisor;

    @OneToMany(mappedBy = "supervisor")
    private List<Staff> operators;
}