package org.mustapha.ClientPortal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mustapha.ClientPortal.dto.request.ClientProductDtoRequest;
import org.mustapha.ClientPortal.dto.response.ClientProductDtoResponse;
import org.mustapha.ClientPortal.model.ClientProduct;

@Mapper(componentModel = "spring")
public interface ClientProductMapper {


    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "product.id", target = "productId")
    ClientProductDtoResponse toDto(ClientProduct clientProduct);

    @Mapping(source = "clientId", target = "client.id")
    @Mapping(source = "productId", target = "product.id")
    ClientProduct toEntity(ClientProductDtoRequest request);
}
