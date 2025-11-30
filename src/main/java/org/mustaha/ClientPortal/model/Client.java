package org.mustaha.ClientPortal.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.mustaha.ClientPortal.model.ClientProduct;
import org.mustaha.ClientPortal.model.User;

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

    // For Bonus: Total Income Calculation
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<ClientProduct> subscribedProducts;

    @OneToMany(mappedBy = "client")
    private List<Claim> claims;
}