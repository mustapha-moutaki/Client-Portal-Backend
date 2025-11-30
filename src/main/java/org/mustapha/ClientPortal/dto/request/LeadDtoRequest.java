package org.mustapha.ClientPortal.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.mustapha.ClientPortal.enums.LeadStatus;

@Data
public class LeadDtoRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String contactInfo;

    private String source;

    private LeadStatus status;

    private String notes;

    @NotNull
    private Long assignedOperatorId; // thee Operator responsible

}
