package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.dto.UserDTO;
import com.kodilla.ecommercee.exception.UserNotFoundException;
import com.kodilla.ecommercee.mapper.UserMapper;
import com.kodilla.ecommercee.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetAllUsers() {
        // Given
        User user = new User();
        UserDTO.UserDto userDto = new UserDTO.UserDto(1L, "John", "Doe", "hash", "john@doe.com",
                false, LocalDateTime.now(), null, null, null, List.of(), List.of());

        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.mapToUserDto(user)).thenReturn(userDto);

        // When
        List<UserDTO.UserDto> result = userService.getAllUsers();

        // Then
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).firstName());
    }


    @Test
    void testGetUserById() {
        // Given
        User user = new User();
        UserDTO.UserDto userDto = new UserDTO.UserDto(1L, "John", "Doe", "hash", "john@doe.com",
                false, LocalDateTime.now(), null, null, null, List.of(), List.of());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.mapToUserDto(user)).thenReturn(userDto);

        // When
        UserDTO.UserDto result = userService.getUserById(1L);

        // Then
        assertEquals("John", result.firstName());
    }

    @Test
    void testGetUserByIdNotFound() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void testCreateUser() {
        // Given
        UserDTO.UserDto inputDto = new UserDTO.UserDto(null, "John", "Doe", "hash", "john@doe.com",
                false, null, null, null, null, List.of(), List.of());

        User savedUser = new User();
        savedUser.setId(1L);
        UserDTO.UserDto outputDto = new UserDTO.UserDto(1L, "John", "Doe", "hash", "john@doe.com",
                false, LocalDateTime.now(), null, null, null, List.of(), List.of());

        when(userMapper.mapToUser(inputDto)).thenReturn(new User());
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(userMapper.mapToUserDto(savedUser)).thenReturn(outputDto);

        // When
        UserDTO.UserDto result = userService.createUser(inputDto);

        // Then
        assertEquals(1L, result.id());
        assertNotNull(result.createdAt());
    }

    @Test
    void testUpdateUser() {
        // Given
        User existingUser = new User();
        UserDTO.UserDto inputDto = new UserDTO.UserDto(1L, "John", "Updated", "newHash", "john@updated.com",
                true, null, null, null, null, List.of(), List.of());

        User updatedUser = new User();
        UserDTO.UserDto outputDto = new UserDTO.UserDto(1L, "John", "Updated", "newHash", "john@updated.com",
                true, LocalDateTime.now(), null, null, null, List.of(), List.of());

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(updatedUser);
        when(userMapper.mapToUserDto(updatedUser)).thenReturn(outputDto);

        // When
        UserDTO.UserDto result = userService.updateUser(1L, inputDto);

        // Then
        assertEquals("Updated", result.lastName());
        assertEquals("newHash", result.passwordHash());
        assertTrue(result.blocked());
    }

    @Test
    void testBlockUser() {
        // Given
        User user = new User();
        user.setBlocked(false);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.mapToUserDto(user)).thenReturn(new UserDTO.UserDto(1L, "John", "Doe", "hash", "john@doe.com",
                true, LocalDateTime.now(), null, null, null, List.of(), List.of()));

        // When
        UserDTO.UserDto result = userService.blockUser(1L, true);

        // Then
        assertTrue(result.blocked());
    }

    @Test
    void testGenerateToken() {
        // Given
        User user = new User();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.mapToTokenDto(user)).thenReturn(new UserDTO.Token(1L, "generated-token",
                LocalDateTime.now(), LocalDateTime.now().plusHours(1)));

        // When
        UserDTO.Token result = userService.generateToken(1L);

        // Then
        assertNotNull(result.token());
        assertNotNull(result.tokenExpiresAt());
    }

    @Test
    void testDeleteUser() {
        // Given
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        // When & Then
        assertDoesNotThrow(() -> userService.deleteUser(1L));
        verify(userRepository, times(1)).deleteById(1L);
    }
}