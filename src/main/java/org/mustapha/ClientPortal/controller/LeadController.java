package org.mustapha.ClientPortal.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.mustapha.ClientPortal.dto.request.LeadDtoRequest;
import org.mustapha.ClientPortal.dto.response.LeadDtoResponse;
import org.mustapha.ClientPortal.service.LeadService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leads")
@RequiredArgsConstructor
@Tag(name = "Lead", description = "API for managing leads")
public class LeadController {

    private final LeadService leadService;

    @PostMapping
    public LeadDtoResponse createLead(@RequestBody LeadDtoRequest request) {
        return leadService.createLead(request);
    }

    @PutMapping("/{id}")
    public LeadDtoResponse updateLead(@PathVariable Long id, @RequestBody LeadDtoRequest request) {
        return leadService.updateLead(id, request);
    }

    @GetMapping("/{id}")
    public LeadDtoResponse getLeadById(@PathVariable Long id) {
        return leadService.getLeadById(id);
    }

    @GetMapping
    public Page<LeadDtoResponse> getAllLeads(Pageable pageable) {
        return leadService.getAllLeads(pageable);
    }

    @DeleteMapping("/{id}")
    public void deleteLead(@PathVariable Long id) {
        leadService.deleteLead(id);
    }
}
