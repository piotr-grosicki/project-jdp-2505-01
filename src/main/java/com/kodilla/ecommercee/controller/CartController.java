package com.kodilla.ecommercee.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    @PostMapping
    public ResponseEntity<Long> createEmptyCart() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<Object> getCartContents(@PathVariable Long cartId) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{cartId}/products/{productId}")
    public ResponseEntity<Object> addProductToCart(
            @PathVariable Long cartId, @PathVariable Long productId) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cartId}/products/{productId}")
    public ResponseEntity<Void> removeProductFromCart(
            @PathVariable Long cartId, @PathVariable Long productId) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{cartId}/orders")
    public ResponseEntity<Long> convertCartToOrder(@PathVariable Long cartId) {
        return ResponseEntity.ok().build();
    }
}