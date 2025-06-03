package com.kodilla.ecommercee.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {

    @GetMapping
    public ResponseEntity<Void> getAllGroups() {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> createGroup(@RequestBody Object group) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<Void> getGroup(@PathVariable Long groupId) {
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<Void> updateGroup(@PathVariable Long groupId, @RequestBody Object group) {
        return ResponseEntity.ok().build();
    }
}
