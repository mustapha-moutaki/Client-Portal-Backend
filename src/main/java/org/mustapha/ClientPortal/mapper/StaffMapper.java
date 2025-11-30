package org.mustapha.ClientPortal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mustapha.ClientPortal.dto.request.StaffDtoRequest;
import org.mustapha.ClientPortal.dto.response.StaffDtoResponse;
import org.mustapha.ClientPortal.model.Staff;

@Mapper(componentModel = "spring")
public interface StaffMapper {


    @Mapping(source = "supervisor.id", target = "supervisorId")
    @Mapping(target = "operatorIds", ignore = true) // ignore list of operators in response
    StaffDtoResponse toDto(Staff staff);

    Staff toEntity(StaffDtoRequest request);
}
