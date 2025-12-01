package org.mustapha.ClientPortal.service.impl;

import lombok.RequiredArgsConstructor;
import org.mustapha.ClientPortal.dto.request.UserDtoRequest;
import org.mustapha.ClientPortal.dto.response.UserDtoResponse;
import org.mustapha.ClientPortal.exception.ResourceNotFoundException;
import org.mustapha.ClientPortal.mapper.UserMapper;
import org.mustapha.ClientPortal.model.User;
import org.mustapha.ClientPortal.repository.UserRepository;
import org.mustapha.ClientPortal.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDtoResponse createUser(UserDtoRequest request) {
        User user = userMapper.toEntity(request);
        // add check username or role
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    @Override
    public UserDtoResponse updateUser(Long userId, UserDtoRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // updated filed
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        // update password
        User updated = userRepository.save(user);
        return userMapper.toDto(updated);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        userRepository.delete(user);
    }

    @Override
    public UserDtoResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return userMapper.toDto(user);
    }

    @Override
    public Page<UserDtoResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toDto);
    }
}
