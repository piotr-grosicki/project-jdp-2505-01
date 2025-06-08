package com.kodilla.ecommercee.dto;

import java.time.LocalDateTime;
import java.util.List;

public class UserDTO {
    public record UserDto(
            Long id,
            String firstName,
            String lastName,
            String passwordHash,
            String email,
            boolean blocked,
            LocalDateTime createdAt,
            String token,
            LocalDateTime tokenCreatedAt,
            LocalDateTime tokenExpiresAt,
            List<Long> orders,
            List<Long> carts
    ) {
    }
    public record Token(
            Long userId,
            String token,
            LocalDateTime tokenCreatedAt,
            LocalDateTime tokenExpiresAt
    ) {
    }
}
