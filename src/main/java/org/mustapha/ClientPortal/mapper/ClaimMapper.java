package org.mustapha.ClientPortal.mapper;

import org.mapstruct.*;
import org.mustapha.ClientPortal.dto.request.ClaimDtoRequest;
import org.mustapha.ClientPortal.dto.response.ClaimDtoResponse;
import org.mustapha.ClientPortal.model.Claim;
import org.mustapha.ClientPortal.model.Client;
import org.mustapha.ClientPortal.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ClaimMapper {

    @Autowired
    protected ClientRepository clientRepository;

    // --- To Entity ---
    @Mapping(source = "clientId", target = "client", qualifiedByName = "mapClient")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "assignedStaff", ignore = true)
    @Mapping(source = "fileUrl", target = "attachmentUrl")
    @Mapping(target = "status", ignore = true)
    public abstract Claim toEntity(ClaimDtoRequest dto);


    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "assignedStaff.id", target = "assignedStaffId")
    @Mapping(source = "attachmentUrl", target = "fileUrl")

    public abstract ClaimDtoResponse toDto(Claim claim);

    @Named("mapClient")
    protected Client mapClient(Long clientId) {
        if (clientId == null) return null;
        return clientRepository.findById(clientId).orElse(null);
    }
}