package org.mustapha.ClientPortal.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.mustapha.ClientPortal.dto.request.ClaimDtoRequest;
import org.mustapha.ClientPortal.dto.response.ClaimDtoResponse;
import org.mustapha.ClientPortal.service.ClaimService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/claims")
@RequiredArgsConstructor
@Tag(name = "claims", description = "claims management APIs")
public class ClaimController {

    private final ClaimService claimService;

    @PostMapping
    public ResponseEntity<?> createClaim(@Valid @RequestBody ClaimDtoRequest request) {
        var created = claimService.createClaim(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClaimDtoResponse> updateClaim(
            @PathVariable Long id,
            @RequestBody ClaimDtoRequest request
    ) {
        return ResponseEntity.ok(claimService.updateClaim(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClaimDtoResponse> getClaim(@PathVariable Long id) {
        return ResponseEntity.ok(claimService.getClaimById(id));
    }

    @GetMapping
    public ResponseEntity<Page<ClaimDtoResponse>> getAllClaims(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(claimService.getAllClaims(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClaim(@PathVariable Long id) {
        claimService.deleteClaim(id);
        return ResponseEntity.noContent().build();
    }
}
