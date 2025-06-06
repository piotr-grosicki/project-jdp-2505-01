package com.kodilla.ecommercee.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    @PostMapping
    public ResponseEntity<Void> createEmptyCart() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<String> getCartContents(@PathVariable Long cartId) {
        return ResponseEntity.ok("Cart Content: " + cartId);
    }

    @PostMapping("/{cartId}/products/{productId}")
    public ResponseEntity<String> addProductToCart(
            @PathVariable Long cartId, @PathVariable Long productId) {
        return ResponseEntity.ok("Product added to cart:" + productId);
    }

    @DeleteMapping("/{cartId}/products/{productId}")
    public ResponseEntity<Void> removeProductFromCart(
            @PathVariable Long cartId, @PathVariable Long productId) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{cartId}/orders")
    public ResponseEntity<String> convertCartToOrder(@PathVariable Long cartId) {
        return ResponseEntity.ok("Created order: " + cartId);
    }
}