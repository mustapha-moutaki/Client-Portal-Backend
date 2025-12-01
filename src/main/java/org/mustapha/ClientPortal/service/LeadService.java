package org.mustapha.ClientPortal.service;

import org.mustapha.ClientPortal.dto.request.LeadDtoRequest;
import org.mustapha.ClientPortal.dto.response.LeadDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LeadService {

    LeadDtoResponse createLead(LeadDtoRequest request);
    LeadDtoResponse updateLead(Long leadId, LeadDtoRequest request);
    void deleteLead(Long leadId);
    LeadDtoResponse getLeadById(Long leadId);
    Page<LeadDtoResponse> getAllLeads(Pageable pageable);
}
