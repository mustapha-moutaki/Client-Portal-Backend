package org.mustapha.ClientPortal.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ClaimDtoResponse {

    private Long id;

    private String title;
    private BigDecimal amount;
    private LocalDate claimDate;

    private Long clientId;
    private String description;
    private String fileUrl;
    private String status;
    private Long assignedStaffId;
}
