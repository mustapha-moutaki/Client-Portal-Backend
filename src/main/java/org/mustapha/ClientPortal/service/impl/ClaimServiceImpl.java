package org.mustapha.ClientPortal.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mustapha.ClientPortal.dto.request.ClaimDtoRequest;
import org.mustapha.ClientPortal.dto.response.ClaimDtoResponse;
import org.mustapha.ClientPortal.enums.ClaimStatus;
import org.mustapha.ClientPortal.enums.UserRole;
import org.mustapha.ClientPortal.exception.ResourceNotFoundException;
import org.mustapha.ClientPortal.mapper.ClaimMapper;
import org.mustapha.ClientPortal.model.Claim;
import org.mustapha.ClientPortal.model.Client;
import org.mustapha.ClientPortal.model.Staff;
import org.mustapha.ClientPortal.repository.ClaimRepository;
import org.mustapha.ClientPortal.repository.ClientRepository; // Added this
import org.mustapha.ClientPortal.repository.StaffRepository;
import org.mustapha.ClientPortal.service.ClaimService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication; // Added this
import org.springframework.security.core.context.SecurityContextHolder; // Added this
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;
    private final ClaimMapper claimMapper;
    private final StaffRepository staffRepository;
    private final ClientRepository clientRepository; // Injected to find client by username

    @Override
    public ClaimDtoResponse createClaim(ClaimDtoRequest request) {
        Claim claim = claimMapper.toEntity(request);

        // Get current logged-in user details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Check if the current user has CLIENT role
        boolean isClient = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + UserRole.CLIENT.name()));

        if (isClient) {
            // If user is a Client, force association with their account
            Client client = clientRepository.findByUsername(currentUsername)
                    .orElseThrow(() -> new ResourceNotFoundException("Client profile not found for current user"));
            claim.setClient(client);
        }
        // If Admin/Staff, the mapper already handled the clientId from request

        // Set default status if not provided or invalid
        if (request.getStatus() != null && !request.getStatus().isEmpty()) {
            try {
                claim.setStatus(ClaimStatus.valueOf(request.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                claim.setStatus(ClaimStatus.IN_REVIEW);
            }
        } else {
            claim.setStatus(ClaimStatus.IN_REVIEW);
        }

        claimRepository.save(claim);
        return claimMapper.toDto(claim);
    }

    @Override
    public ClaimDtoResponse updateClaim(Long claimId, ClaimDtoRequest request) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found with id: " + claimId));

        // Logic for updates remains the same
        if (request.getTitle() != null) claim.setTitle(request.getTitle());
        if (request.getDescription() != null) claim.setDescription(request.getDescription());

        if (request.getAmount() != null) {
            claim.setAmount(request.getAmount());
        }

        if (request.getClaimDate() != null) {
            claim.setClaimDate(request.getClaimDate());
        }

        if (request.getFileUrl() != null) {
            claim.setAttachmentUrl(request.getFileUrl());
        }

        if (request.getStatus() != null && !request.getStatus().isEmpty()) {
            try {
                claim.setStatus(ClaimStatus.valueOf(request.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                // Ignore invalid status
            }
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
        // Get current logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Check if user is a Client
        boolean isClient = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + UserRole.CLIENT.name()));

        if (isClient) {
            // Return only claims belonging to this client
            return claimRepository.findByClient_Username(currentUsername, pageable)
                    .map(claimMapper::toDto);
        } else {
            // Admin/Staff sees all claims
            return claimRepository.findAll(pageable)
                    .map(claimMapper::toDto);
        }
    }
}