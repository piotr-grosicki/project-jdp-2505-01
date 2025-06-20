package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.dto.UserDTO;
import com.kodilla.ecommercee.exception.UserNotFoundByIdException;
import com.kodilla.ecommercee.mapper.UserMapper;
import com.kodilla.ecommercee.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(userMapper.mapToDtoList(users));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userId)
            throws UserNotFoundByIdException {
        User user = userService.getUser(userId);
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO dto) {
        User user = userMapper.toEntity(dto);
        User created = userService.createUser(user);
        return ResponseEntity.ok(userMapper.toDto(created));
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO dto,
                                              HttpServletRequest request)
            throws UserNotFoundByIdException {

        User authenticatedUser = (User) request.getAttribute("authenticatedUser");
        User user = userMapper.toEntity(dto);
        User updated = userService.updateUser(user, authenticatedUser);
        return ResponseEntity.ok(userMapper.toDto(updated));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId,
                                           HttpServletRequest request)
            throws UserNotFoundByIdException {

        User authenticatedUser = (User) request.getAttribute("authenticatedUser");
        userService.deleteUser(userId, authenticatedUser);
        return ResponseEntity.ok("User with ID " + userId + " was successfully deleted.");
    }

    @PutMapping("/block/{userId}")
    public ResponseEntity<UserDTO> blockUser(@PathVariable Long userId)
            throws UserNotFoundByIdException {
        User blocked = userService.blockUser(userId);
        return ResponseEntity.ok(userMapper.toDto(blocked));
    }

    @PostMapping("/{userId}/keys")
    public ResponseEntity<UserDTO> generateKey(@PathVariable Long userId,
                                               HttpServletRequest request)
            throws UserNotFoundByIdException {

        User authenticatedUser = (User) request.getAttribute("authenticatedUser");
        User updated = userService.generateKey(userId, authenticatedUser);
        return ResponseEntity.ok(userMapper.toDto(updated));
    }
}
