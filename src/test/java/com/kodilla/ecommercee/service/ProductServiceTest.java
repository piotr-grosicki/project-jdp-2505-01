package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.exception.ProductNotFoundByIdException;
import com.kodilla.ecommercee.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("Should return all products from repository")
    void shouldReturnAllProducts() {
        // Given
        Product p1 = Product.builder().id(1L).name("A").build();
        Product p2 = Product.builder().id(2L).name("B").build();
        when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        // When
        List<Product> products = productService.getAllProducts();

        // Then
        assertEquals(2, products.size());
        assertTrue(products.contains(p1));
        assertTrue(products.contains(p2));
        verify(productRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return product when found by id")
    void shouldReturnProductById() throws ProductNotFoundByIdException {
        // Given
        Product p = Product.builder().id(1L).name("A").build();
        when(productRepository.findById(1L)).thenReturn(Optional.of(p));

        // When
        Product result = productService.getProduct(1L);

        // Then
        assertEquals(p, result);
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when product not found by id")
    void shouldThrowWhenProductNotFound() {
        // Given
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(ProductNotFoundByIdException.class, () -> productService.getProduct(1L));
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should create and save new product")
    void shouldCreateAndSaveProduct() {
        // Given
        Product input = Product.builder()
                .name("New").description("Desc").price(new BigDecimal("10.0"))
                .stockQuantity(5L).isActive(true).build();
        Product saved = Product.builder()
                .id(1L).name("New").description("Desc").price(new BigDecimal("10.0"))
                .stockQuantity(5L).isActive(true).createdAt(LocalDateTime.now()).build();
        when(productRepository.save(any(Product.class))).thenReturn(saved);

        // When
        Product result = productService.createProduct(input);

        // Then
        assertNotNull(result.getId());
        assertEquals(saved.getName(), result.getName());
        assertEquals(saved.getDescription(), result.getDescription());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Should update existing product when found")
    void shouldUpdateExistingProduct() throws ProductNotFoundByIdException {
        // Given
        Product existing = Product.builder()
                .id(1L)
                .name("Old")
                .description("OldDesc")
                .price(new BigDecimal("5.0"))
                .stockQuantity(2L)
                .isActive(false)
                .build();
        Product update = Product.builder()
                .id(1L)
                .name("Updated")
                .description("NewDesc")
                .price(new BigDecimal("7.5"))
                .stockQuantity(3L)
                .isActive(true)
                .build();
        when(productRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(productRepository.save(existing)).thenReturn(update);

        // When
        Product result = productService.updateProduct(update);

        // Then
        assertEquals("Updated", result.getName());
        assertEquals("NewDesc", result.getDescription());
        assertTrue(result.isActive());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(existing);
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent product")
    void shouldThrowWhenUpdatingNonexistent() {
        // Given
        Product update = Product.builder().id(1L).build();
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(ProductNotFoundByIdException.class, () -> productService.updateProduct(update));
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should delete product when it exists")
    void shouldDeleteWhenExists() throws ProductNotFoundByIdException {
        // Given
        when(productRepository.existsById(1L)).thenReturn(true);

        // When
        productService.deleteProduct(1L);

        // Then
        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent product")
    void shouldThrowWhenDeletingNonexistent() {
        // Given
        when(productRepository.existsById(1L)).thenReturn(false);

        // When / Then
        assertThrows(ProductNotFoundByIdException.class, () -> productService.deleteProduct(1L));
        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, never()).deleteById(any());
    }
}
