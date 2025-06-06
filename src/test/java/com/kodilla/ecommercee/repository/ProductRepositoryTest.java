package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product product = Product.builder()
            .name("Test Product")
            .price(new BigDecimal("19.99"))
            .description("This is a test product.")
            .stockQuantity(100L)
            .isActive(true)
            .createdAt(LocalDateTime.now())
            .build();

    private Product product2 = Product.builder()
            .name("Test Product")
            .price(new BigDecimal("19.99"))
            .description("This is a test product.")
            .stockQuantity(100L)
            .isActive(true)
            .createdAt(LocalDateTime.now())
            .build();

    @Test
    void testFindById() {
        // Given
        Product savedProduct = productRepository.save(product);

        // When
        Product foundProduct = productRepository.findById(savedProduct.getId()).orElse(null);

        // Then
        try {
            assertNotNull(foundProduct);
            assertEquals(savedProduct.getName(), foundProduct.getName());
            assertEquals(savedProduct.getPrice(), foundProduct.getPrice());
            assertEquals(savedProduct.getDescription(), foundProduct.getDescription());
            assertEquals(savedProduct.getStockQuantity(), foundProduct.getStockQuantity());
            assertTrue(foundProduct.isActive());
        } catch(Exception e) {
            fail("Product retrieval failed: " + e.getMessage());
        } finally {
            productRepository.delete(savedProduct);
        }
    }

    @Test
    void testFindAll() {
        // Given
        productRepository.save(product);
        productRepository.save(product2);

        // When
        Iterable<Product> products = productRepository.findAll();

        // Then
        try {
            assertNotNull(products);
            int count = 0;
            for (Product p : products) {
                count++;
            }
            assertEquals(2, count);
        } catch(Exception e) {
            fail("Product retrieval failed: " + e.getMessage());
        } finally {
            productRepository.delete(product);
            productRepository.delete(product2);
        }
    }

//    @Test
//    void testUpdateProduct() {
//        // Given
//        Product savedProduct = productRepository.save(product);
//        savedProduct.setName("Updated Product");
//        savedProduct.setPrice(new BigDecimal("29.99"));
//
//        // When
//        Product updatedProduct = productRepository.save(savedProduct);
//
//        // Then
//        try {
//            assertNotNull(updatedProduct);
//            assertEquals("Updated Product", updatedProduct.getName());
//            assertEquals(new BigDecimal("29.99"), updatedProduct.getPrice());
//        } catch(Exception e) {
//            fail("Product update failed: " + e.getMessage());
//        } finally {
//            productRepository.delete(updatedProduct);
//        }
//    }
}