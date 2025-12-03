package org.mustapha.ClientPortal.dto.response;

import lombok.Data;

@Data
public class ClaimDtoResponse {

    private Long id;
    private Long clientId;
    private String description;
    private String fileUrl;
    private String status;
    private Long assignedStaffId;
}
