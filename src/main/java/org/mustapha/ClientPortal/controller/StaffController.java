package org.mustapha.ClientPortal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.mustapha.ClientPortal.dto.request.StaffDtoRequest;
import org.mustapha.ClientPortal.dto.response.StaffDtoResponse;
import org.mustapha.ClientPortal.service.StaffService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
@Tag(name = "Staff Management", description = "Endpoints for managing staff members")
public class StaffController {

    private final StaffService staffService;

    @Operation(summary = "Create a new staff member")
    @PostMapping
    public ResponseEntity<StaffDtoResponse> createStaff(@Valid @RequestBody StaffDtoRequest request) {
        StaffDtoResponse created = staffService.createStaff(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing staff member by ID")
    @PutMapping("/{id}")
    public ResponseEntity<StaffDtoResponse> updateStaff(
            @PathVariable("id") Long staffId,
            @Valid @RequestBody StaffDtoRequest request) {
        StaffDtoResponse updated = staffService.updateStaff(staffId, request);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete a staff member by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable("id") Long staffId) {
        staffService.deleteStaff(staffId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get a staff member by ID")
    @GetMapping("/{id}")
    public ResponseEntity<StaffDtoResponse> getStaffById(@PathVariable("id") Long staffId) {
        StaffDtoResponse staff = staffService.getStaffById(staffId);
        return ResponseEntity.ok(staff);
    }

    @Operation(summary = "Get all staff members with pagination")
    @GetMapping
    public ResponseEntity<Page<StaffDtoResponse>> getAllStaff(Pageable pageable) {
        Page<StaffDtoResponse> staffPage = staffService.getAllStaff(pageable);
        return ResponseEntity.ok(staffPage);
    }
}
