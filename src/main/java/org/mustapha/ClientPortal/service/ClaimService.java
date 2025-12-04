package org.mustapha.ClientPortal.service;

import org.mustapha.ClientPortal.dto.request.ClaimDtoRequest;
import org.mustapha.ClientPortal.dto.response.ClaimDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ClaimService {

    // Updated to support File Upload and User Email link
//    ClaimDtoResponse createClaim(ClaimDtoRequest request, MultipartFile file, String userEmail);
    ClaimDtoResponse createClaim(ClaimDtoRequest request, MultipartFile file, String username);

    // Standard Update
    ClaimDtoResponse updateClaim(Long claimId, ClaimDtoRequest request);

    // Supervisor Action
    ClaimDtoResponse assignClaim(Long claimId, Long operatorId);

    // Operator Action
    ClaimDtoResponse updateStatus(Long claimId, String status);

    void deleteClaim(Long claimId);

    ClaimDtoResponse getClaimById(Long claimId);

    // One method to rule them all (Role based logic)
    Page<ClaimDtoResponse> getClaimsBasedOnRole(Pageable pageable, Authentication auth);
    List<ClaimDtoResponse> getClaimsByClientId(Long clientId);
}