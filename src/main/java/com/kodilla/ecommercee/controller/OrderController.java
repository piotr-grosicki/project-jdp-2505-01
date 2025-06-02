package com.kodilla.ecommercee.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @GetMapping("/all")
    public ResponseEntity<List<Object>> getAllOrders() {
        return ResponseEntity.ok(new ArrayList<>());
    }

    @PostMapping("/create")
    public ResponseEntity<String> createOrder() {
        return ResponseEntity.ok("Order created");
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<String> getOrderById(@PathVariable String orderId) {
        return ResponseEntity.ok(orderId);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<String> updateOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(orderId);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String orderId) {
        return ResponseEntity.ok().build();
    }
}
