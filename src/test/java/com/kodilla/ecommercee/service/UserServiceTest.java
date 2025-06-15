package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.exception.UserNotFoundByIdException;
import com.kodilla.ecommercee.exception.UserNotFoundByMailException;
import com.kodilla.ecommercee.repository.UserRepository;
import com.kodilla.ecommercee.security.AccessGuard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

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

    @Mock
    private AccessGuard accessGuard;

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
                .tokenExpiresAt(LocalDateTime.now().plusHours(1))
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
                .isInstanceOf(UserNotFoundByIdException.class)
                .hasMessageContaining("99");
    }

    @Test
    void shouldReturnUserByEmail() throws UserNotFoundByMailException {
        when(repository.findByEmail("adam@nowak.pl")).thenReturn(Optional.of(user));
        User result = service.getUserByEmail("adam@nowak.pl");
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void shouldCreateUser() {
        when(repository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        User created = service.createUser(user);
        verify(repository).save(any(User.class));
        assertThat(created.getFirstName()).isEqualTo("Adam");
        assertThat(created.getToken()).isNotBlank();
        assertThat(created.getTokenExpiresAt()).isAfter(created.getTokenCreatedAt());
    }

    @Test
    void shouldBlockUser() throws UserNotFoundByIdException {
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        when(repository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        User blocked = service.blockUser(1L);
        assertThat(blocked.isBlocked()).isTrue();
    }

    @Test
    void shouldGenerateKey() throws UserNotFoundByIdException {
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        when(repository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        doNothing().when(accessGuard).checkOwner(1L, 1L);
        User withKey = service.generateKey(1L, user);
        assertThat(withKey.getToken()).isNotBlank();
    }

    @Test
    void shouldUpdateUser() throws UserNotFoundByIdException {
        User edited = User.builder().id(1L).firstName("Jan").lastName("Nowak").email("jan@nowak.pl").build();
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        when(repository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        doNothing().when(accessGuard).checkOwner(1L, 1L);
        User result = service.updateUser(edited, user);
        assertThat(result.getFirstName()).isEqualTo("Jan");
        assertThat(result.getEmail()).isEqualTo("jan@nowak.pl");
    }

    @Test
    void shouldThrowAccessDeniedWhenUserTriesToUpdateOtherUser() {
        User edited = User.builder().id(1L).build();
        User requester = User.builder().id(2L).build();
        doThrow(new AccessDeniedException("Access denied"))
                .when(accessGuard).checkOwner(2L, 1L);
        assertThatThrownBy(() -> service.updateUser(edited, requester))
                .isInstanceOf(AccessDeniedException.class)
                .hasMessage("Access denied");
    }

    @Test
    void shouldDeleteUser() throws UserNotFoundByIdException {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(accessGuard).checkOwner(1L, 1L);
        service.deleteUser(1L, user);
        verify(repository).deleteById(1L);
    }

    @Test
    void shouldThrowWhenDeletingNonExistentUser() {
        when(repository.existsById(999L)).thenReturn(false);
        doNothing().when(accessGuard).checkOwner(1L, 999L);
        assertThatThrownBy(() -> service.deleteUser(999L, user))
                .isInstanceOf(UserNotFoundByIdException.class)
                .hasMessageContaining("999");
    }

    @Test
    void shouldReturnAllUsers() {
        when(repository.findAll()).thenReturn(Arrays.asList(user, user));
        List<User> result = service.getAllUsers();
        assertThat(result).hasSize(2);
    }
}
