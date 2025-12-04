package org.mustapha.ClientPortal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mustapha.ClientPortal.dto.request.ClaimDtoRequest;
import org.mustapha.ClientPortal.dto.response.ClaimDtoResponse;
import org.mustapha.ClientPortal.service.ClaimService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/claims")
@RequiredArgsConstructor
@Tag(name = "Claims Management", description = "Endpoints for Clients, Operators, Supervisors, and Admins")
public class ClaimController {

    private final ClaimService claimService;

    // 1. Create Claim (Client Only - Multipart/Form-Data)
//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @PreAuthorize("hasAuthority('CLIENT')")
//    @Operation(summary = "Create a new claim", description = "Upload a file and JSON data. Only for Clients.")
//    public ResponseEntity<ClaimDtoResponse> createClaim(
//            @RequestPart("claim") @Valid ClaimDtoRequest request,
//            @RequestPart(value = "file", required = false) MultipartFile file,
//            Authentication authentication
//    ) {
//        var created = claimService.createClaim(request, file, authentication.getName());
//        return new ResponseEntity<>(created, HttpStatus.CREATED);
//    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    @Operation(summary = "Create a new claim", description = "Upload a file and JSON data. Only for Clients.")
    public ResponseEntity<ClaimDtoResponse> createClaim(
            @RequestPart("claim") @Valid ClaimDtoRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file,
            Authentication authentication
    ) {

        ClaimDtoResponse created = claimService.createClaim(request, file, authentication.getName());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }


    // 2. View All/My Claims (Smart Filter)
    @GetMapping
    @Operation(summary = "Get claims list", description = "Returns claims based on role: Clients see theirs, Operators see assigned, Admins see all.")
    public ResponseEntity<Page<ClaimDtoResponse>> getAllClaims(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(claimService.getClaimsBasedOnRole(pageable, authentication));
    }

    // 3. View Single Claim
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'OPERATOR', 'CLIENT')")
    public ResponseEntity<ClaimDtoResponse> getClaim(@PathVariable Long id) {
        return ResponseEntity.ok(claimService.getClaimById(id));
    }

    // 4. Update Details (Title/Desc/Amount)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'OPERATOR')")
    @Operation(summary = "Update claim details", description = "Edit title, description, or amount.")
    public ResponseEntity<ClaimDtoResponse> updateClaim(
            @PathVariable Long id,
            @RequestBody ClaimDtoRequest request
    ) {
        return ResponseEntity.ok(claimService.updateClaim(id, request));
    }

    // 5. Update Status (Workflow: SUBMITTED -> IN_REVIEW -> RESOLVED)
    // ADDED THIS: Needed for Operators to work quickly
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'OPERATOR')")
    @Operation(summary = "Update claim status", description = "Change status to IN_REVIEW or RESOLVED")
    public ResponseEntity<ClaimDtoResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        return ResponseEntity.ok(claimService.updateStatus(id, status));
    }

    // 6. Assign Operator (Supervisor/Admin Only)
    @PutMapping("/{id}/assign/{operatorId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    @Operation(summary = "Assign claim", description = "Assign a claim to a specific Operator.")
    public ResponseEntity<ClaimDtoResponse> assignClaim(
            @PathVariable Long id,
            @PathVariable Long operatorId
    ) {
        return ResponseEntity.ok(claimService.assignClaim(id, operatorId));
    }

    // 7. Delete Claim (Admin Only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteClaim(@PathVariable Long id) {
        claimService.deleteClaim(id);
        return ResponseEntity.noContent().build();
    }
}