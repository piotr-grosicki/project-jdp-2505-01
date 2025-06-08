package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User mapToUser(UserDTO.UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        return User.builder()
                .id(userDto.id())
                .firstName(userDto.firstName() != null ? userDto.firstName() : "")
                .lastName(userDto.lastName() != null ? userDto.lastName() : "")
                .passwordHash(userDto.passwordHash() != null ? userDto.passwordHash() : "")
                .email(userDto.email() != null ? userDto.email() : "")
                .isBlocked(userDto.blocked())
                .createdAt(userDto.createdAt() != null ? userDto.createdAt() : null)
                .token(userDto.token() != null ? userDto.token() : null)
                .tokenCreatedAt(userDto.tokenCreatedAt() != null ? userDto.tokenCreatedAt() : null)
                .tokenExpiresAt(userDto.tokenExpiresAt() != null ? userDto.tokenExpiresAt() : null)
                .build();
    }

    public UserDTO.UserDto mapToUserDto(User user) {
        if (user == null) {
            return null;
        }

        return new UserDTO.UserDto(
                user.getId(),
                user.getFirstName() != null ? user.getFirstName() : "",
                user.getLastName() != null ? user.getLastName() : "",
                user.getPasswordHash() != null ? user.getPasswordHash() : "",
                user.getEmail() != null ? user.getEmail() : "",
                user.isBlocked(),
                user.getCreatedAt() != null ? user.getCreatedAt() : null,
                user.getToken() != null ? user.getToken() : null,
                user.getTokenCreatedAt() != null ? user.getTokenCreatedAt() : null,
                user.getTokenExpiresAt() != null ? user.getTokenExpiresAt() : null,
                user.getOrders() != null
                        ? user.getOrders().stream()
                        .map(order -> order != null && order.getId() != null ? order.getId() : null)
                        .filter(id -> id != null)
                        .collect(Collectors.toList())
                        : List.of(),
                user.getCarts() != null
                        ? user.getCarts().stream()
                        .map(cart -> cart != null && cart.getId() != null ? cart.getId() : null)
                        .filter(id -> id != null)
                        .collect(Collectors.toList())
                        : List.of()
        );
    }

    public UserDTO.Token mapToTokenDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO.Token(
                user.getId(),
                user.getToken() != null ? user.getToken() : "",
                user.getTokenCreatedAt() != null ? user.getTokenCreatedAt() : null,
                user.getTokenExpiresAt() != null ? user.getTokenExpiresAt() : null
        );
    }

    public List<UserDTO.UserDto> mapToUserDtoList(List<User> users) {
        if (users == null) {
            return List.of();
        }
        return users.stream()
                .map(this::mapToUserDto)
                .filter(dto -> dto != null)
                .collect(Collectors.toList());
    }
}
