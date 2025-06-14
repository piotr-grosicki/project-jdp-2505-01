package com.kodilla.ecommercee.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CartDTOTest {

    @Test
    void shouldCreateCartDTOAndGetAllFields() {
        // Given
        List<Long> products = List.of(1L, 2L, 3L);
        Long expectedId = 1L;
        String expectedStatus = "PENDING";
        LocalDateTime expectedCreatedAt =
                LocalDateTime.of(2025, 6, 7, 12, 0,0);

        // When
        CartDTO cart = new CartDTO(expectedId, expectedStatus, expectedCreatedAt, products, null, null);

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
        List<Long> products = List.of(1L, 2L, 3L);
        LocalDateTime createdAt = LocalDateTime.of(2025, 6, 7, 12, 0,0);
        CartDTO cart1 =
                new CartDTO(1L, "NEW", createdAt, products, null, null);
        CartDTO cart2 =
                new CartDTO(1L, "NEW", createdAt, products, null, null);
        CartDTO cart3 =
                new CartDTO(2L, "NEW", createdAt, products, null, null);

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
        List<Long> products = List.of(1L, 2L);
        Long id = 42L;
        String status = "COMPLETED";
        LocalDateTime createdAt = LocalDateTime.of(2025, 6, 7, 12, 0,0);
        CartDTO cart = new CartDTO(id, status, createdAt, products, null, null);

        // When
        String repr = cart.toString();

        // Then
        assertTrue(repr.contains("id=" + id), "toString should contain the id");
        assertTrue(repr.contains("status=" + status), "toString should contain the status");
        assertTrue(repr.contains("createdAt=" + createdAt), "toString should contain the creation time");
        assertTrue(repr.contains("products="), "toString should include products list");
    }
}