package org.mustapha.ClientPortal.dto.response;

import lombok.Data;
import org.mustapha.ClientPortal.enums.LeadStatus;

@Data
public class LeadDtoResponse {

    private Long id;
    private String name;
    private String contactInfo;
    private String source;
    private LeadStatus status;
    private String notes;
    private Long assignedOperatorId;

}
