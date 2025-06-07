package com.kodilla.ecommercee.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CartDTOTest {

    @Test
    void shouldCreateCartDTOAndGetAllFields() {
        // Given
        ProductDTO product = mock(ProductDTO.class);
        List<ProductDTO> products = List.of(product);
        Long expectedId = 1L;
        String expectedStatus = "PENDING";
        String expectedCreatedAt = "2025-06-07T12:00:00";

        // When
        CartDTO cart = new CartDTO(expectedId, expectedStatus, expectedCreatedAt, products);

        // Then
        assertEquals(expectedId, cart.id());
        assertEquals(expectedStatus, cart.status());
        assertEquals(expectedCreatedAt, cart.createdAt());
        assertEquals(products, cart.products());
        assertNotNull(cart.toString());
    }

    @Test
    void shouldImplementEqualsAndHashCodeCorrectly() {
        // Given
        ProductDTO product = mock(ProductDTO.class);
        List<ProductDTO> products = List.of(product);
        CartDTO cart1 = new CartDTO(1L, "NEW", "2025-06-07T10:00:00", products);
        CartDTO cart2 = new CartDTO(1L, "NEW", "2025-06-07T10:00:00", products);
        CartDTO cart3 = new CartDTO(2L, "NEW", "2025-06-07T10:00:00", products);

        // Then
        assertEquals(cart1, cart2, "Carts with same data should be equal");
        assertEquals(cart1.hashCode(), cart2.hashCode(), "Hash codes should match for equal objects");
        assertNotEquals(cart1, cart3, "Carts with different id should not be equal");
    }

    @Test
    void toStringShouldContainAllFieldValues() {
        // Given
        ProductDTO product1 = mock(ProductDTO.class);
        ProductDTO product2 = mock(ProductDTO.class);
        List<ProductDTO> products = List.of(product1, product2);
        Long id = 42L;
        String status = "COMPLETED";
        String createdAt = "2025-06-07T15:30:00";
        CartDTO cart = new CartDTO(id, status, createdAt, products);

        // When
        String repr = cart.toString();

        // Then
        assertTrue(repr.contains("id=" + id), "toString should contain the id");
        assertTrue(repr.contains("status=" + status), "toString should contain the status");
        assertTrue(repr.contains("createdAt=" + createdAt), "toString should contain the creation time");
        assertTrue(repr.contains("products="), "toString should include products list");
    }
}