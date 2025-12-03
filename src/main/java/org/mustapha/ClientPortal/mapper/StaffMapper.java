package org.mustapha.ClientPortal.mapper;

import org.mapstruct.*;
import org.mustapha.ClientPortal.dto.request.StaffDtoRequest;
import org.mustapha.ClientPortal.dto.response.StaffDtoResponse;
import org.mustapha.ClientPortal.model.Staff;
import org.mustapha.ClientPortal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class StaffMapper {

    @Autowired
    protected UserRepository userRepository;

    // ✅ FIXED: Removed the 'ignore' lines for role, username, email, password.
    // Now MapStruct will automatically map them because names match.

    @Mapping(source = "supervisorId", target = "supervisor", qualifiedByName = "mapSupervisor")
    @Mapping(target = "active", ignore = true)    // Keep this if you set active manually in Service
    @Mapping(target = "operators", ignore = true) // Keep this
    public abstract Staff toEntity(StaffDtoRequest dto);

    @Mapping(source = "supervisor.id", target = "supervisorId")
    @Mapping(source = "operators", target = "operatorIds", qualifiedByName = "mapOperatorsToIds")
    public abstract StaffDtoResponse toDto(Staff staff);

    @Named("mapSupervisor")
    protected Staff mapSupervisor(Long supervisorId) {
        // If ID is null or 0, return null
        if (supervisorId == null || supervisorId == 0) return null;

        return userRepository.findById(supervisorId)
                .filter(u -> u instanceof Staff)
                .map(u -> (Staff) u)
                .orElse(null);
    }

    @Named("mapOperatorsToIds")
    protected List<Long> mapOperatorsToIds(List<Staff> operators) {
        if (operators == null) return null;
        return operators.stream().map(Staff::getId).collect(Collectors.toList());
    }
}