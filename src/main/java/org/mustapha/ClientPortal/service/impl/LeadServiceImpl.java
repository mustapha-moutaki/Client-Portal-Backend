package org.mustapha.ClientPortal.service.impl;

import lombok.RequiredArgsConstructor;
import org.mustapha.ClientPortal.dto.request.LeadDtoRequest;
import org.mustapha.ClientPortal.dto.response.LeadDtoResponse;
import org.mustapha.ClientPortal.exception.ResourceNotFoundException;
import org.mustapha.ClientPortal.mapper.LeadMapper;
import org.mustapha.ClientPortal.model.Lead;
import org.mustapha.ClientPortal.repository.LeadRepository;
import org.mustapha.ClientPortal.service.LeadService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeadServiceImpl implements LeadService {

    private final LeadRepository leadRepository;
    private final LeadMapper leadMapper;

    @Override
    public LeadDtoResponse createLead(LeadDtoRequest request) {
        Lead lead = leadMapper.toEntity(request);
        leadRepository.save(lead);
        return leadMapper.toDto(lead);
    }

    @Override
    public LeadDtoResponse updateLead(Long leadId, LeadDtoRequest request) {
        Lead lead = leadRepository.findById(leadId)
                .orElseThrow(() -> new ResourceNotFoundException("Lead not found with id: " + leadId));

        lead.setName(request.getName());
        lead.setContactInfo(request.getContactInfo());
        lead.setSource(request.getSource());
        lead.setStatus(request.getStatus());
        lead.setNotes(request.getNotes());

        leadRepository.save(lead);
        return leadMapper.toDto(lead);
    }

    @Override
    public void deleteLead(Long leadId) {
        Lead lead = leadRepository.findById(leadId)
                .orElseThrow(() -> new ResourceNotFoundException("Lead not found with id: " + leadId));
        leadRepository.delete(lead);
    }

    @Override
    public LeadDtoResponse getLeadById(Long leadId) {
        Lead lead = leadRepository.findById(leadId)
                .orElseThrow(() -> new ResourceNotFoundException("Lead not found with id: " + leadId));
        return leadMapper.toDto(lead);
    }

    @Override
    public Page<LeadDtoResponse> getAllLeads(Pageable pageable) {
        return leadRepository.findAll(pageable)
                .map(leadMapper::toDto);
    }
}
