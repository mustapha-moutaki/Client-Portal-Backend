package org.mustapha.ClientPortal.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClaimDtoRequest {

    @NotNull
    private Long clientId;

    @NotBlank
    private String description;

    private String fileUrl; // PDF, JPG, PNG, ..
}
