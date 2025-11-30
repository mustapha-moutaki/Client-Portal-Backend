package org.mustapha.ClientPortal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mustapha.ClientPortal.dto.request.UserDtoRequest;
import org.mustapha.ClientPortal.dto.response.UserDtoResponse;
import org.mustapha.ClientPortal.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDtoResponse toDto(User user);

    User toEntity(UserDtoRequest request);
}
