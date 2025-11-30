package org.mustapha.ClientPortal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mustapha.ClientPortal.dto.request.ProductDtoRequest;
import org.mustapha.ClientPortal.dto.response.ProductDtoResponse;
import org.mustapha.ClientPortal.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDtoResponse toDto(Product product);

    Product toEntity(ProductDtoRequest request);
}
