package org.mustapha.ClientPortal.dto.response;

import lombok.Data;
import org.mustapha.ClientPortal.enums.UserRole;

@Data
public class UserDtoResponse {

    private Long id;
    private String username;
    private String email;
    private UserRole role;
    private boolean active;

}

