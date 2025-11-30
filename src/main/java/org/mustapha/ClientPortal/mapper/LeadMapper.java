package org.mustapha.ClientPortal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mustapha.ClientPortal.dto.request.LeadDtoRequest;
import org.mustapha.ClientPortal.dto.response.LeadDtoResponse;
import org.mustapha.ClientPortal.model.Lead;


@Mapper(componentModel = "spring")
public interface LeadMapper {

    @Mapping(source = "assignedOperator.id", target = "assignedOperatorId")
    LeadDtoResponse toDto(Lead lead);

    @Mapping(source = "assignedOperatorId", target = "assignedOperator.id")
    Lead toEntity(LeadDtoRequest request);
}
