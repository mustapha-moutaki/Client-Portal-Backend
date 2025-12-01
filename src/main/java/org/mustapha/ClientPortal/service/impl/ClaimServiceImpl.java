package org.mustapha.ClientPortal.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mustapha.ClientPortal.dto.request.ClaimDtoRequest;
import org.mustapha.ClientPortal.dto.response.ClaimDtoResponse;
import org.mustapha.ClientPortal.exception.ResourceNotFoundException;
import org.mustapha.ClientPortal.mapper.ClaimMapper;
import org.mustapha.ClientPortal.model.Claim;
import org.mustapha.ClientPortal.repository.ClaimRepository;
import org.mustapha.ClientPortal.service.ClaimService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;
    private final ClaimMapper claimMapper;

    @Override
    public ClaimDtoResponse createClaim(ClaimDtoRequest request) {
        Claim claim = claimMapper.toEntity(request);
        claimRepository.save(claim);
        return claimMapper.toDto(claim);
    }

    @Override
    public ClaimDtoResponse updateClaim(Long claimId, ClaimDtoRequest request) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found with id: " + claimId));

        claim.setTitle(request.getTitle());
        claim.setDescription(request.getDescription());
        claim.setStatus(request.getStatus());

        claimRepository.save(claim);
        return claimMapper.toDto(claim);
    }

    @Override
    public void deleteClaim(Long claimId) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found with id: " + claimId));
        claimRepository.delete(claim);
    }

    @Override
    public ClaimDtoResponse getClaimById(Long claimId) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found with id: " + claimId));
        return claimMapper.toDto(claim);
    }

    @Override
    public Page<ClaimDtoResponse> getAllClaims(Pageable pageable) {
        return claimRepository.findAll(pageable)
                .map(claimMapper::toDto);
    }
}
