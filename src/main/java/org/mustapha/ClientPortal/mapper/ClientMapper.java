package org.mustapha.ClientPortal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mustapha.ClientPortal.dto.request.ClientDtoRequest;
import org.mustapha.ClientPortal.dto.response.ClientDtoResponse;
import org.mustapha.ClientPortal.model.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {


    @Mapping(target = "subscribedProductIds", ignore = true) // ignore list of client products
    @Mapping(target = "claimIds", ignore = true) // ignore list of claims
    ClientDtoResponse toDto(Client client);

    Client toEntity(ClientDtoRequest request);
}
