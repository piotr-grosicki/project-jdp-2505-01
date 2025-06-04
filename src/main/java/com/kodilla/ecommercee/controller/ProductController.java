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
    public ResponseEntity<Void> getProduct(@PathVariable Long productId) {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody String product) {
        return ResponseEntity.ok("product" + product);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<String> updateProduct(
            @PathVariable Long productId, @RequestBody String updatedProduct) {
        return ResponseEntity.ok("updatedProduct"+ updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        return ResponseEntity.ok().build();
    }
}