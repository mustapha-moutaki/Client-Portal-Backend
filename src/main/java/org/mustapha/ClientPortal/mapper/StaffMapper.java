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
// here we use abstarct mapper so we can use speacial methods to convert between objects and primitives;;
    @Autowired
    protected UserRepository userRepository;

    @Mapping(source = "supervisorId", target = "supervisor", qualifiedByName = "mapSupervisor")
    @Mapping(target = "username", ignore = true) // inherited fields
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "operators", ignore = true)
    public abstract Staff toEntity(StaffDtoRequest dto);

    @Mapping(source = "supervisor.id", target = "supervisorId")
    @Mapping(source = "operators", target = "operatorIds", qualifiedByName = "mapOperatorsToIds")
    public abstract StaffDtoResponse toDto(Staff staff);

    @Named("mapSupervisor")
    protected Staff mapSupervisor(Long supervisorId) {
        if (supervisorId == null) return null;
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
