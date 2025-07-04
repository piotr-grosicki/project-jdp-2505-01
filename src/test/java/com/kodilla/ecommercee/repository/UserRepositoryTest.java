package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User buildUser() {
        return User.builder()
                .firstName("John")
                .lastName("Doe")
                .passwordHash("hashedPassword")
                .email("john.doe@test.com")
                .isBlocked(false)
                .createdAt(LocalDateTime.now())
                .token("testToken")
                .tokenCreatedAt(LocalDateTime.now())
                .tokenExpiresAt(LocalDateTime.now().plusHours(1))
                .build();
    }

    @Test
    void testCreateNewUser() {
        // Given
        User user = buildUser();

        // When
        User savedUser = userRepository.save(user);

        // Then
        try {
            assertNotNull(savedUser.getId());
            assertEquals("John", savedUser.getFirstName());
            assertEquals("Doe", savedUser.getLastName());
            assertEquals("hashedPassword", savedUser.getPasswordHash());
        } catch (Exception e) {
            fail("User should be saved successfully, but an exception occurred: " + e.getMessage());
        } finally {
            userRepository.delete(savedUser);
        }
    }

    @Test
    void testFindUserById() {
        // Given
        User user = buildUser();
        User savedUser = userRepository.save(user);

        // When
        Long userId = savedUser.getId();
        User foundUser = userRepository.findById(userId).orElse(null);

        // Then
        try {
            assertNotNull(foundUser);
            assertEquals(userId, foundUser.getId());
        } catch (Exception e) {
            fail("User should be found successfully, but an exception occurred: " + e.getMessage());
        } finally {
            userRepository.delete(savedUser);
        }
    }

    @Test
    void testFindUserByEmail() {
        // Given
        User user = buildUser();
        User savedUser = userRepository.save(user);

        // When
        User foundUser = userRepository.findByEmail(savedUser.getEmail()).orElse(null);

        // Then
        try {
            assertNotNull(foundUser);
            assertEquals(savedUser.getEmail(), foundUser.getEmail());
        } catch (Exception e) {
            fail("User should be found by email successfully, but an exception occurred: " + e.getMessage());
        } finally {
            userRepository.delete(savedUser);
        }
    }

//    @Test
//    void testBlockUser() {
//        // Given
//        User user = buildUser();
//        User savedUser = userRepository.save(user);
//
//        // When
//        savedUser.setBlocked(true);
//        User updatedUser = userRepository.save(savedUser);
//
//        // Then
//        try {
//            assertTrue(updatedUser.isBlocked());
//        } catch (Exception e) {
//            fail("User should be blocked successfully, but an exception occurred: " + e.getMessage());
//        } finally {
//            userRepository.delete(savedUser);
//            userRepository.delete(updatedUser);
//        }
//    }

    @Test
    void testDeleteUser() {
        // Given
        User user = buildUser();
        User savedUser = userRepository.save(user);

        // When
        userRepository.delete(savedUser);
        Long userId = savedUser.getId();
        User foundUser = userRepository.findById(userId).orElse(null);

        // Then
        try {
            assertNull(foundUser);
        } catch (Exception e) {
            fail("User should be deleted successfully, but an exception occurred: " + e.getMessage());
        }
    }
}