package org.mustapha.ClientPortal.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class ClientDtoResponse {

    private Long id;
    private String username;
    private String email;
    private String companyName;
    private String phone;
    private String address;
    private boolean active;

    private List<Long> subscribedProductIds; //  client products
    private List<Long> claimIds;             // client's claims if ixist for sure

}
