package com.kodilla.ecommercee.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody String user) {
        return ResponseEntity.ok(user);
    }

    @PutMapping("/block/{userId}")
    public ResponseEntity<String> blockUser(@PathVariable String userId) {
        return ResponseEntity.ok("User blocked: " + userId);
    }

    @PostMapping("/{userId}/keys")
    public ResponseEntity<String> generateKey(@PathVariable String userId) {
        return ResponseEntity.ok("Key generated for user: " + userId);
    }
}
