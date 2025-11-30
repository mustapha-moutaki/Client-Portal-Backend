package org.mustapha.ClientPortal.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class StaffDtoResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private Long supervisorId;
    private List<Long> operatorIds;

}
