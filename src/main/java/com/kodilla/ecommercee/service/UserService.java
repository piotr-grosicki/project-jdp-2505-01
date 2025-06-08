package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.dto.UserDTO;
import com.kodilla.ecommercee.exception.UserNotFoundException;
import com.kodilla.ecommercee.mapper.UserMapper;
import com.kodilla.ecommercee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDTO.UserDto> getAllUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(userMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public UserDTO.UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return userMapper.mapToUserDto(user);
    }

    public UserDTO.UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return userMapper.mapToUserDto(user);
    }

    public UserDTO.UserDto createUser(UserDTO.UserDto dto) {
        User user = userMapper.mapToUser(dto);
        user.setCreatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        return userMapper.mapToUserDto(savedUser);
    }

    public UserDTO.UserDto updateUser(Long id, UserDTO.UserDto dto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        existingUser.setFirstName(dto.firstName());
        existingUser.setLastName(dto.lastName());
        existingUser.setPasswordHash(dto.passwordHash());
        existingUser.setEmail(dto.email());
        existingUser.setBlocked(dto.blocked());

        User updatedUser = userRepository.save(existingUser);
        return userMapper.mapToUserDto(updatedUser);
    }

    public UserDTO.UserDto blockUser(Long id, boolean isBlocked) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        user.setBlocked(isBlocked);
        User updatedUser = userRepository.save(user);
        return userMapper.mapToUserDto(updatedUser);
    }

    public UserDTO.Token generateToken(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        String token = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = now.plusHours(1);

        user.setToken(token);
        user.setTokenCreatedAt(now);
        user.setTokenExpiresAt(expiresAt);

        User updatedUser = userRepository.save(user);
        return userMapper.mapToTokenDto(updatedUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}