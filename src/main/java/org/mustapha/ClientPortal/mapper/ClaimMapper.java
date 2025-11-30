package org.mustapha.ClientPortal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mustapha.ClientPortal.dto.request.ClaimDtoRequest;
import org.mustapha.ClientPortal.dto.response.ClaimDtoResponse;
import org.mustapha.ClientPortal.model.Claim;

@Mapper(componentModel = "spring")
public interface ClaimMapper {

    @Mapping(source = "client.id", target = "clientId")
    ClaimDtoResponse toDto(Claim claim);

    @Mapping(source = "clientId", target = "client.id")
    Claim toEntity(ClaimDtoRequest request);
}
