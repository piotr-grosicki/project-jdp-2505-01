package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.exception.UserNotFoundByIdException;
import com.kodilla.ecommercee.exception.UserNotFoundByMailException;
import com.kodilla.ecommercee.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
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
                .createdAt(LocalDateTime.now().minusDays(1))
                .token(UUID.randomUUID().toString())
                .tokenCreatedAt(LocalDateTime.now().minusHours(2))
                .tokenExpiresAt(LocalDateTime.now().minusHours(1))
                .build();
    }

    @Test
    void shouldReturnUserById() throws UserNotFoundByIdException {
        // given
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        // when
        User result = service.getUser(1L);
        // then
        assertThat(result.getLastName()).isEqualTo("Nowak");
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        // given
        when(repository.findById(99L)).thenReturn(Optional.empty());
        // when / then
        assertThatThrownBy(() -> service.getUser(99L))
                .isInstanceOf(UserNotFoundByIdException.class)
                .hasMessageContaining("99");
    }

    @Test
    void shouldReturnUserByEmail() throws UserNotFoundByMailException {
        // given
        when(repository.findByEmail("adam@nowak.pl")).thenReturn(Optional.of(user));
        // when
        User result = service.getUserByEmail("adam@nowak.pl");
        // then
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void shouldCreateUser() {
        // given
        when(repository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        // when
        User created = service.createUser(user);
        // then
        verify(repository).save(any(User.class));
        assertThat(created.getFirstName()).isEqualTo("Adam");
        assertThat(created.getEmail()).isEqualTo("adam@nowak.pl");
        assertThat(created.getCreatedAt()).isNotNull();
        assertThat(created.getToken()).isNotBlank();
        assertThat(created.getTokenCreatedAt()).isNotNull();
        assertThat(created.getTokenExpiresAt())
                .isAfter(created.getTokenCreatedAt());
    }

    @Test
    void shouldBlockUser() throws UserNotFoundByIdException {
        // given
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        when(repository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        // when
        User blocked = service.blockUser(1L);
        // then
        assertThat(blocked.isBlocked()).isTrue();
    }

    @Test
    void shouldGenerateKey() throws UserNotFoundByIdException {
        // given
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        when(repository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        // when
        User withKey = service.generateKey(1L);
        // then
        assertThat(withKey.getToken()).isNotNull();
        assertThat(UUID.fromString(withKey.getToken())).isInstanceOf(UUID.class);
        assertThat(withKey.getTokenExpiresAt())
                .isAfter(withKey.getTokenCreatedAt());
    }

    @Test
    void shouldUpdateUser() throws UserNotFoundByIdException {
        // given
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        when(repository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        user.setFirstName("Jan");
        user.setLastName("Nowak");
        user.setEmail("jan@nowak.pl");
        // when
        User updated = service.updateUser(user);
        // then
        assertThat(updated.getFirstName()).isEqualTo("Jan");
        assertThat(updated.getLastName()).isEqualTo("Nowak");
        assertThat(updated.getEmail()).isEqualTo("jan@nowak.pl");
    }

    @Test
    void shouldDeleteUser() throws UserNotFoundByIdException {
        // given
        when(repository.existsById(1L)).thenReturn(true);
        // when
        service.deleteUser(1L);
        // then
        verify(repository).deleteById(1L);
    }

    @Test
    void shouldThrowWhenDeleteNonExisting() {
        // given
        when(repository.existsById(2L)).thenReturn(false);
        // when / then
        assertThatThrownBy(() -> service.deleteUser(2L))
                .isInstanceOf(UserNotFoundByIdException.class)
                .hasMessageContaining("2");
    }

    @Test
    void shouldGetAllUsers() {
        // given
        List<User> list = Arrays.asList(user, user);
        when(repository.findAll()).thenReturn(list);
        // when
        List<User> result = service.getAllUsers();
        // then
        assertThat(result).hasSize(2);
        verify(repository).findAll();
    }
}
