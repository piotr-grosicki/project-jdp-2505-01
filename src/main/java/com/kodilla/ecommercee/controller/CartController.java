package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.dto.CartDTO;
import com.kodilla.ecommercee.mapper.CartMapper;
import com.kodilla.ecommercee.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
public class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;

    @PostMapping
    public ResponseEntity<CartDTO> createEmptyCart() {
        CartDTO cartDto = cartMapper.mapToCartDto(cartService.createEmptyCart());
        return ResponseEntity.ok(cartDto);
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