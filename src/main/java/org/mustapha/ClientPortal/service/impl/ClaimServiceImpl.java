package org.mustapha.ClientPortal.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mustapha.ClientPortal.dto.request.ClaimDtoRequest;
import org.mustapha.ClientPortal.dto.response.ClaimDtoResponse;
import org.mustapha.ClientPortal.enums.ClaimStatus;
import org.mustapha.ClientPortal.exception.ResourceNotFoundException;
import org.mustapha.ClientPortal.mapper.ClaimMapper;
import org.mustapha.ClientPortal.model.Claim;
import org.mustapha.ClientPortal.model.Client;
import org.mustapha.ClientPortal.model.Staff; // Assuming Operator is Staff or User
import org.mustapha.ClientPortal.model.User;
import org.mustapha.ClientPortal.repository.ClaimRepository;
import org.mustapha.ClientPortal.repository.ClientRepository;
import org.mustapha.ClientPortal.repository.StaffRepository; // Or UserRepository
import org.mustapha.ClientPortal.repository.UserRepository;
import org.mustapha.ClientPortal.service.ClaimService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;
    private final ClaimMapper claimMapper;
    private final StaffRepository staffRepository; // Used to find Operators
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    // --- 1. CREATE CLAIM (with File) ---
//        @Override
//        public ClaimDtoResponse createClaim(ClaimDtoRequest request, MultipartFile file, String userEmail) {
//            Claim claim = claimMapper.toEntity(request);
//
//            // Link Client by Email
//            Client client = clientRepository.findByEmail(userEmail)
//                    .orElseThrow(() -> new ResourceNotFoundException("Client not found with email: " + userEmail));
//            claim.setClient(client);
//
//            // Handle File
//            if (file != null && !file.isEmpty()) {
//                String fileUrl = saveFile(file);
//                claim.setAttachmentUrl(fileUrl);
//            }
//
//            // Default Status
//            claim.setStatus(ClaimStatus.SUBMITTED);
//
//            claimRepository.save(claim);
//            return claimMapper.toDto(claim);
//        }
    // داخل ClaimServiceImpl أو مكان createClaim
    @Override
    public ClaimDtoResponse createClaim(ClaimDtoRequest request, MultipartFile file, String username) {
        Claim claim = claimMapper.toEntity(request);


        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        String userEmail = user.getEmail();


        Client client = clientRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with email: " + userEmail));

        claim.setClient(client);

        // Handle File Upload
        if (file != null && !file.isEmpty()) {
            String fileUrl = saveFile(file);
            claim.setAttachmentUrl(fileUrl);
        }

        // Default Status
        claim.setStatus(ClaimStatus.SUBMITTED);

        claimRepository.save(claim);
        return claimMapper.toDto(claim);
    }


    // --- 2. ASSIGN CLAIM (Supervisor) ---
    @Override
    public ClaimDtoResponse assignClaim(Long claimId, Long operatorId) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));

        Staff operator = staffRepository.findById(operatorId)
                .orElseThrow(() -> new ResourceNotFoundException("Operator not found"));

        claim.setAssignedStaff(operator); // Ensure Claim entity has this field (Type: Staff or User)
        claim.setStatus(ClaimStatus.IN_REVIEW);

        return claimMapper.toDto(claimRepository.save(claim));
    }

    // --- 3. UPDATE STATUS (Operator) ---
    @Override
    public ClaimDtoResponse updateStatus(Long claimId, String status) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));

        try {
            claim.setStatus(ClaimStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status provided");
        }

        return claimMapper.toDto(claimRepository.save(claim));
    }

    // --- 4. GET CLAIMS (Smart Role Logic) ---
    @Override
    public Page<ClaimDtoResponse> getClaimsBasedOnRole(Pageable pageable, Authentication auth) {
        // Get the Authority (Role)
        String role = auth.getAuthorities().iterator().next().getAuthority();
        // Get the Email
        String email = auth.getName();

        // Check Roles (using loose matching to handle ROLE_ prefix differences)
        if (role.contains("CLIENT")) {
            return claimRepository.findByClientEmail(email, pageable).map(claimMapper::toDto);
        } else if (role.contains("OPERATOR")) {
            return claimRepository.findByAssignedStaffEmail(email, pageable).map(claimMapper::toDto);
        } else {
            return claimRepository.findAll(pageable).map(claimMapper::toDto);
        }
    }

    // --- 5. STANDARD UPDATE ---
    @Override
    public ClaimDtoResponse updateClaim(Long claimId, ClaimDtoRequest request) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));

        if (request.getTitle() != null) claim.setTitle(request.getTitle());
        if (request.getDescription() != null) claim.setDescription(request.getDescription());
        if (request.getStatus() != null) {
            try { claim.setStatus(ClaimStatus.valueOf(request.getStatus().toUpperCase())); }
            catch (Exception e) {}
        }

        return claimMapper.toDto(claimRepository.save(claim));
    }

    @Override
    public void deleteClaim(Long claimId) {
        if (!claimRepository.existsById(claimId)) throw new ResourceNotFoundException("Claim not found");
        claimRepository.deleteById(claimId);
    }

    @Override
    public ClaimDtoResponse getClaimById(Long claimId) {
        return claimRepository.findById(claimId)
                .map(claimMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));
    }

    // --- Helper: Save File ---
    private String saveFile(MultipartFile file) {
        try {
            String folder = "uploads/claims/";
            Files.createDirectories(Paths.get(folder));
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get(folder + filename);
            Files.copy(file.getInputStream(), path);
            return path.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file");
        }
    }

    public List<ClaimDtoResponse> getClaimsByClientId(Long clientId) {
        List<Claim> claims = claimRepository.findByClientId(clientId); // you need this repository method
        return claims.stream()
                .map(claimMapper::toDto)
                .toList();
    }



}