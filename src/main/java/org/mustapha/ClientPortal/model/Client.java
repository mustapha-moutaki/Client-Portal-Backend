package org.mustapha.ClientPortal.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
@PrimaryKeyJoinColumn(name = "user_id")
@Builder
public class Client extends User {


    private String companyName;
    private String phone;
    private String address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<ClientProduct> subscribedProducts;

    @OneToMany(mappedBy = "client")
    private List<Claim> claims;

    // Minimal constructor for MapStruct mapping
    public Client(Long id) {
        super.setId(id);
    }
}