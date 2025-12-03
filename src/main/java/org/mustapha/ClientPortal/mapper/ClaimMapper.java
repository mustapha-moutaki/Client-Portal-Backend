package org.mustapha.ClientPortal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mustapha.ClientPortal.dto.request.ClaimDtoRequest;
import org.mustapha.ClientPortal.dto.response.ClaimDtoResponse;
import org.mustapha.ClientPortal.model.Claim;

@Mapper(componentModel = "spring")
public interface ClaimMapper {

    // Entity -> DTO
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "attachmentUrl", target = "fileUrl")
    @Mapping(source = "assignedStaff.id", target = "assignedStaffId")
    ClaimDtoResponse toDto(Claim claim);

    // DTO -> Entity
    @Mapping(target = "client", expression = "java(new Client(request.getClientId()))")
    @Mapping(source = "fileUrl", target = "attachmentUrl")
    @Mapping(source = "assignedStaffId", target = "assignedStaff.id")
    Claim toEntity(ClaimDtoRequest request);
}

