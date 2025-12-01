package org.mustapha.ClientPortal.service;

import org.mustapha.ClientPortal.dto.request.StaffDtoRequest;
import org.mustapha.ClientPortal.dto.response.StaffDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StaffService {

    StaffDtoResponse createStaff(StaffDtoRequest request);
    StaffDtoResponse updateStaff(Long staffId, StaffDtoRequest request);
    void deleteStaff(Long staffId);
    StaffDtoResponse getStaffById(Long staffId);
    Page<StaffDtoResponse> getAllStaff(Pageable pageable);

}
