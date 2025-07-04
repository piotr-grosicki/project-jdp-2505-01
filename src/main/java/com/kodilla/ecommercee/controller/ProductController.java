package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.dto.ProductDTO;
import com.kodilla.ecommercee.exception.ProductNotFoundByIdException;
import com.kodilla.ecommercee.mapper.ProductMapper;
import com.kodilla.ecommercee.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(productMapper.mapToProductDTOList(products));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long productId)
            throws ProductNotFoundByIdException {
        Product product = productService.getProduct(productId);
        return ResponseEntity.ok(productMapper.toProductDTO(product));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO dto) {
        Product product = productMapper.toProduct(dto);
        return ResponseEntity.ok(productMapper.toProductDTO(productService.createProduct(product)));
    }

    @PutMapping
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO dto)
            throws ProductNotFoundByIdException {
        Product product = productMapper.toProduct(dto);
        Product updatedProduct = productService.updateProduct(product);
        return ResponseEntity.ok(productMapper.toProductDTO(updatedProduct));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId)
            throws ProductNotFoundByIdException {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Product with ID " + productId + " was successfully deleted.");

    }
}