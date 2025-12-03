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
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final StaffMapper staffMapper;

    @Override
    public StaffDtoResponse createStaff(StaffDtoRequest request) {
        Optional<Staff> staffexist = staffRepository.findByEmail(request.getEmail());
        if(staffexist.isPresent()){
         throw  new RuntimeException("this email is already exist");
        }
        Staff staff = Staff.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(UserRole.OPERATOR) // default role
                .active(true)
                .build();

        staffRepository.save(staff);

        return staffMapper.toDto(staff);
    }


    @Override
    public StaffDtoResponse updateStaff(Long staffId, StaffDtoRequest request) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with id: " + staffId));

        staff.setFirstName(request.getFirstName());
        staff.setLastName(request.getLastName());

        if (request.getSupervisorId() != null) {
            Staff supervisor = staffRepository.findById(request.getSupervisorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Supervisor not found with id: " + request.getSupervisorId()));
            staff.setSupervisor(supervisor);
        } else {
            staff.setSupervisor(null);
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
