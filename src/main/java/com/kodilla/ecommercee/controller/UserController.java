package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.dto.UserDTO;
import com.kodilla.ecommercee.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO.UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO.UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/email")
    public ResponseEntity<UserDTO.UserDto> getUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PostMapping
    public ResponseEntity<UserDTO.UserDto> createUser(@RequestBody UserDTO.UserDto dto) {
        return ResponseEntity.ok(userService.createUser(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO.UserDto> updateUser(@PathVariable Long id, @RequestBody UserDTO.UserDto dto) {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @PatchMapping("/{id}/block")
    public ResponseEntity<UserDTO.UserDto> blockUser(@PathVariable Long id, @RequestParam boolean isBlocked) {
        return ResponseEntity.ok(userService.blockUser(id, isBlocked));
    }

    @PostMapping("/{id}/token")
    public ResponseEntity<UserDTO.Token> generateToken(@PathVariable Long id) {
        return ResponseEntity.ok(userService.generateToken(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
