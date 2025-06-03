package com.kodilla.ecommercee.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @GetMapping
    public ResponseEntity<Void> getAllProducts() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Long> getProduct(@PathVariable Long productId) {
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody Object product) {
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Object> updateProduct(
            @PathVariable Long productId, @RequestBody Object updatedProduct) {
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        return ResponseEntity.ok().build();
    }
}