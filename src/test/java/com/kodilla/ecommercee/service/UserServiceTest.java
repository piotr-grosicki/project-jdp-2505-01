package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.exception.UserNotFoundByIdException;
import com.kodilla.ecommercee.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import com.kodilla.ecommercee.exception.UserNotFoundByMailException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    private User user;

    @BeforeEach
    void init() {
        user = User.builder()
                .id(1L)
                .firstName("Adam")
                .lastName("Nowak")
                .email("adam@nowak.pl")
                .isBlocked(false)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldReturnUserById() throws UserNotFoundByIdException {
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        User result = service.getUser(1L);
        assertThat(result.getLastName()).isEqualTo("Nowak");
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getUser(99L))
                .isInstanceOf(UserNotFoundByIdException.class);
    }

    @Test
    void shouldReturnUserByEmail() throws UserNotFoundByMailException {
        when(repository.findByEmail("adam@nowak.pl")).thenReturn(Optional.of(user));
        User result = service.getUserByEmail("adam@nowak.pl");
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void shouldCreateUser() {
        when(repository.save(any(User.class))).thenReturn(user);
        User created = service.createUser(user);
        verify(repository).save(user);
        assertThat(created.getCreatedAt()).isNotNull();
    }

    @Test
    void shouldBlockUser() throws UserNotFoundByIdException {
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        when(repository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));
        User blocked = service.blockUser(1L);
        assertThat(blocked.isBlocked()).isTrue();
    }

    @Test
    void shouldGenerateKey() throws UserNotFoundByIdException {
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        when(repository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));
        User withKey = service.generateKey(1L);
        assertThat(withKey.getToken()).isNotNull();
        assertThat(UUID.fromString(withKey.getToken())).isInstanceOf(UUID.class);
        assertThat(withKey.getTokenExpiresAt())
                .isAfter(withKey.getTokenCreatedAt());
    }
}
