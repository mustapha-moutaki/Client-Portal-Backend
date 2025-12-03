package org.mustapha.ClientPortal.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class StaffDtoRequest {
    @NotBlank(message = "first name is required")
    private String firstName;

    @NotBlank(message = "lastName is required")
    private String lastName;

    @NotBlank
    private String username;

    @Email
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 5, message = "password must be at least 5 characters")
    private String password;

    private Long supervisorId;
}

