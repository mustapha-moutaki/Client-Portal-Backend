package org.mustapha.ClientPortal.service;

import org.mustapha.ClientPortal.dto.request.ClaimDtoRequest;
import org.mustapha.ClientPortal.dto.response.ClaimDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClaimService {
    ClaimDtoResponse createClaim(ClaimDtoRequest request);
    ClaimDtoResponse updateClaim(Long claimId, ClaimDtoRequest request);
    void deleteClaim(Long claimId);
    ClaimDtoResponse getClaimById(Long claimId);
    Page<ClaimDtoResponse> getAllClaims(Pageable pageable);
}
