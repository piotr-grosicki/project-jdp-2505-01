package com.kodilla.ecommercee.dto;

import java.time.LocalDateTime;

public record UserDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        boolean isBlocked,
        LocalDateTime createdAt,
        String token,
        LocalDateTime tokenCreatedAt,
        LocalDateTime tokenExpiresAt
) {}
