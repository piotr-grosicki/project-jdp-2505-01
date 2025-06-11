package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDTO toDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.isBlocked(),
                user.getCreatedAt(),
                user.getToken(),
                user.getTokenCreatedAt(),
                user.getTokenExpiresAt()
        );
    }

    public User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        return User.builder()
                .id(dto.id())
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .isBlocked(dto.isBlocked())
                .createdAt(dto.createdAt())
                .token(dto.token())
                .tokenCreatedAt(dto.tokenCreatedAt())
                .tokenExpiresAt(dto.tokenExpiresAt())
                .build();
    }

    public List<UserDTO> mapToDtoList(List<User> users) {
        return users.stream()
                        .map(this::toDto)
                        .collect(Collectors.toList());
    }
}
