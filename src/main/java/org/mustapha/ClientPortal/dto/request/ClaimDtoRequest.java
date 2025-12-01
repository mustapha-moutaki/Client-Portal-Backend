package org.mustapha.ClientPortal.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.mustapha.ClientPortal.enums.ClaimStatus;

@Data
public class ClaimDtoRequest {

    @NotBlank(message = "client is required")
    private Long clientId;

    private String title;
    private ClaimStatus status = ClaimStatus.IN_REVIEW;
    @NotBlank
    private String description;

    private String fileUrl; // PDF, JPG, PNG, ..
}
