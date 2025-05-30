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
    public ResponseEntity<Object> createOrder() {
        return ResponseEntity.ok(new Object());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Object> getOrderById(@PathVariable String orderId) {
        return ResponseEntity.ok(new Object());
    }

    @PutMapping("/update?orderId={orderId}")
    public ResponseEntity<Object> updateOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(new Object());
    }

    @DeleteMapping("/delete?orderId={orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String orderId) {
        return ResponseEntity.ok().build();
    }
}
