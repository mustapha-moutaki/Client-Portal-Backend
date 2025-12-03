package org.mustapha.ClientPortal.service.impl;

import lombok.RequiredArgsConstructor;
import org.mustapha.ClientPortal.dto.request.StaffDtoRequest;
import org.mustapha.ClientPortal.dto.response.StaffDtoResponse;
import org.mustapha.ClientPortal.enums.UserRole;
import org.mustapha.ClientPortal.exception.ResourceNotFoundException;
import org.mustapha.ClientPortal.mapper.StaffMapper;
import org.mustapha.ClientPortal.model.Staff;
import org.mustapha.ClientPortal.repository.StaffRepository;
import org.mustapha.ClientPortal.service.StaffService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder; // ✅ 1. Important import
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final StaffMapper staffMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public StaffDtoResponse createStaff(StaffDtoRequest request) {
        // Check if email already exists
        if(staffRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("This email already exists");
        }

        // Check if username already exists
        if(staffRepository.findByUsername(request.getUsername()).isPresent()){
            throw new RuntimeException("This username already exists");
        }

        // 3. Set role: if null, default to OPERATOR
        UserRole roleToSave = request.getRole() != null ? request.getRole() : UserRole.OPERATOR;

        Staff staff = Staff.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .email(request.getEmail())
                // ✅ 4. Encode password
                .password(passwordEncoder.encode(request.getPassword()))
                .role(roleToSave)
                .active(true)
                .build();

        // If a supervisor is selected
        if (request.getSupervisorId() != null && request.getSupervisorId() != 0) {
            Staff supervisor = staffRepository.findById(request.getSupervisorId())
                    .orElse(null); // or throw exception if mandatory
            staff.setSupervisor(supervisor);
        }

        staffRepository.save(staff);

        return staffMapper.toDto(staff);
    }

    @Override
    public StaffDtoResponse updateStaff(Long staffId, StaffDtoRequest request) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with id: " + staffId));

        staff.setFirstName(request.getFirstName());
        staff.setLastName(request.getLastName());

        // Note: Do not update password here unless you create a separate function

        // Update role if provided
        if (request.getRole() != null) {
            staff.setRole(request.getRole());
        }

        if (request.getSupervisorId() != null && request.getSupervisorId() != 0) {
            Staff supervisor = staffRepository.findById(request.getSupervisorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Supervisor not found with id: " + request.getSupervisorId()));
            staff.setSupervisor(supervisor);
        } else {
            staff.setSupervisor(null); // remove supervisor
        }

        return staffMapper.toDto(staffRepository.save(staff));
    }

    @Override
    public void deleteStaff(Long staffId) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with id: " + staffId));
        staffRepository.delete(staff);
    }

    @Override
    public StaffDtoResponse getStaffById(Long staffId) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with id: " + staffId));
        return staffMapper.toDto(staff);
    }

    @Override
    public Page<StaffDtoResponse> getAllStaff(Pageable pageable) {
        return staffRepository.findAll(pageable)
                .map(staffMapper::toDto);
    }
}
