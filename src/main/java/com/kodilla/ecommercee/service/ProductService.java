package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.exception.CartNotFoundException;
import com.kodilla.ecommercee.exception.ProductNotFoundException;
import com.kodilla.ecommercee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long productId) throws ProductNotFoundException {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    public Product createProduct(Product product) {
        Product newProduct = Product.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .isActive(product.isActive())
                .createdAt(LocalDateTime.now())
                .build();
        return productRepository.save(newProduct);
    }

    public Product updateProduct(Product product) throws ProductNotFoundException {
        Product existingProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new ProductNotFoundException(product.getId()));
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStockQuantity(product.getStockQuantity());
        existingProduct.setActive(product.isActive());
        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long productId) throws  ProductNotFoundException {
        if (!productRepository.existsById(productId))
                throw new ProductNotFoundException(productId);
        productRepository.deleteById(productId);
    }
}
