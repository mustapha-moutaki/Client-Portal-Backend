package org.mustapha.ClientPortal.service;

import org.mustapha.ClientPortal.dto.request.UserDtoRequest;
import org.mustapha.ClientPortal.dto.response.UserDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserDtoResponse createUser(UserDtoRequest request);

    UserDtoResponse updateUser(Long userId, UserDtoRequest request);

    void deleteUser(Long userId);

    UserDtoResponse getUserById(Long userId);

    Page<UserDtoResponse> getAllUsers(Pageable pageable);
}
