package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testMapToUser() {
        // Given
        UserDTO.UserDto userDto = new UserDTO.UserDto(
                1L, "John", "Doe", "hash123", "john@doe.com",
                false, LocalDateTime.now(), "token123",
                LocalDateTime.now(), LocalDateTime.now().plusHours(1),
                List.of(1L, 2L), List.of(3L, 4L));

        // When
        User user = userMapper.mapToUser(userDto);

        // Then
        assertEquals(1L, user.getId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("hash123", user.getPasswordHash());
        assertEquals("john@doe.com", user.getEmail());
        assertFalse(user.isBlocked());
        assertNotNull(user.getCreatedAt());
        assertEquals("token123", user.getToken());
    }

    @Test
    void testMapToUserDto() {
        // Given
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPasswordHash("hash123");
        user.setEmail("john@doe.com");
        user.setBlocked(false);
        user.setCreatedAt(LocalDateTime.now());
        user.setToken("token123");
        user.setTokenCreatedAt(LocalDateTime.now());
        user.setTokenExpiresAt(LocalDateTime.now().plusHours(1));

        // When
        UserDTO.UserDto userDto = userMapper.mapToUserDto(user);

        // Then
        assertEquals(1L, userDto.id());
        assertEquals("John", userDto.firstName());
        assertEquals("Doe", userDto.lastName());
        assertEquals("hash123", userDto.passwordHash());
        assertEquals("john@doe.com", userDto.email());
        assertFalse(userDto.blocked());
        assertNotNull(userDto.createdAt());
        assertEquals("token123", userDto.token());
    }

    @Test
    void testMapToTokenDto() {
        // Given
        User user = new User();
        user.setId(1L);
        user.setToken("token123");
        user.setTokenCreatedAt(LocalDateTime.now());
        user.setTokenExpiresAt(LocalDateTime.now().plusHours(1));

        // When
        UserDTO.Token tokenDto = userMapper.mapToTokenDto(user);

        // Then
        assertEquals(1L, tokenDto.userId());
        assertEquals("token123", tokenDto.token());
        assertNotNull(tokenDto.tokenCreatedAt());
        assertNotNull(tokenDto.tokenExpiresAt());
    }

    @Test
    void testMapToUserDtoList() {
        // Given
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);
        List<User> users = List.of(user1, user2);

        // When
        List<UserDTO.UserDto> userDtos = userMapper.mapToUserDtoList(users);

        // Then
        assertEquals(2, userDtos.size());
        assertEquals(1L, userDtos.get(0).id());
        assertEquals(2L, userDtos.get(1).id());
    }
}