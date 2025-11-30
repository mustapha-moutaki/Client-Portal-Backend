package org.mustapha.ClientPortal.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StaffDtoRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private Long supervisorId; // optional, only for operators

}
