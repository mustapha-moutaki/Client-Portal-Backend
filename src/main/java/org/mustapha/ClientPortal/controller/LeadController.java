package org.mustapha.ClientPortal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "Create a new lead", description = "Allows a client to create a new lead.")
    @PostMapping
    public LeadDtoResponse createLead(@RequestBody LeadDtoRequest request) {
        return leadService.createLead(request);
    }

    @Operation(summary = "Update an existing lead", description = "Update lead details by ID. Only admin, supervisor, or operator can update.")
    @PutMapping("/{id}")
    public LeadDtoResponse updateLead(
            @Parameter(description = "ID of the lead to update", required = true) @PathVariable Long id,
            @RequestBody LeadDtoRequest request) {
        return leadService.updateLead(id, request);
    }

    @Operation(summary = "Get lead by ID", description = "Retrieve lead details by its ID.")
    @GetMapping("/{id}")
    public LeadDtoResponse getLeadById(
            @Parameter(description = "ID of the lead", required = true) @PathVariable Long id) {
        return leadService.getLeadById(id);
    }

    @Operation(summary = "Get all leads", description = "Retrieve a paginated list of all leads.")
    @GetMapping
    public Page<LeadDtoResponse> getAllLeads(Pageable pageable) {
        return leadService.getAllLeads(pageable);
    }

    @Operation(summary = "Delete a lead", description = "Delete a lead by its ID. Only admin can delete.")
    @DeleteMapping("/{id}")
    public void deleteLead(
            @Parameter(description = "ID of the lead to delete", required = true) @PathVariable Long id) {
        leadService.deleteLead(id);
    }

    @Operation(summary = "Update lead status", description = "Change the status of a lead by its ID. Status must be one of NEW, CONTACTED, QUALIFIED, LOST, CONVERTED.")
    @PatchMapping("/{id}/status")
    public LeadDtoResponse updateLeadStatus(
            @Parameter(description = "ID of the lead", required = true) @PathVariable Long id,
            @Parameter(description = "New status value", required = true, example = "CONTACTED") @RequestParam String status
    ) {
        return leadService.updateLeadStatus(id, status);
    }
}
