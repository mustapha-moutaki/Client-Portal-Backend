package org.mustapha.ClientPortal.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList; // Added import
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
@PrimaryKeyJoinColumn(name = "user_id")

public class Client extends User {

    private String companyName;
    private String phone;
    private String address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @Builder.Default // Initialize list to avoid nulls
    private List<ClientProduct> subscribedProducts = new ArrayList<>();

    @OneToMany(mappedBy = "client")
    @Builder.Default
    private List<Claim> claims = new ArrayList<>();
}