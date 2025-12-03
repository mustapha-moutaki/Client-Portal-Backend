package org.mustapha.ClientPortal.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mustapha.ClientPortal.dto.request.ClaimDtoRequest;
import org.mustapha.ClientPortal.dto.response.ClaimDtoResponse;
import org.mustapha.ClientPortal.exception.ResourceNotFoundException;
import org.mustapha.ClientPortal.mapper.ClaimMapper;
import org.mustapha.ClientPortal.model.Claim;
import org.mustapha.ClientPortal.model.Staff;
import org.mustapha.ClientPortal.repository.ClaimRepository;
import org.mustapha.ClientPortal.repository.StaffRepository;
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
    private final StaffRepository staffRepository;

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

        // Update all fields if present in the request
        if (request.getTitle() != null) {
            claim.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            claim.setDescription(request.getDescription());
        }

        if (request.getStatus() != null) {
            claim.setStatus(request.getStatus());
        }

        if (request.getFileUrl() != null) {
            claim.setAttachmentUrl(request.getFileUrl());
        }

        if (request.getAssignedStaffId() != null) {
            Staff staff = staffRepository.findById(request.getAssignedStaffId())
                    .orElseThrow(() -> new ResourceNotFoundException("Staff not found with id: " + request.getAssignedStaffId()));
            claim.setAssignedStaff(staff);
        }

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
