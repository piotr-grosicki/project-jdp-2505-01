package com.kodilla.ecommercee.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProductDTOTest {

    @Test
    @DisplayName("Should create ProductDTO and access all fields correctly")
    void shouldCreateProductDTOAndAccessFields() {
        // Given
        Long id = 1L;
        String name = "Test Product";
        String description = "Description of test product";
        BigDecimal price = new BigDecimal("19.99");
        Long quantity = 5L;
        Boolean isActive = true;
        LocalDateTime createdAt = LocalDateTime.of(2025, 6, 8, 12, 0, 0);

        // When
        ProductDTO dto = new ProductDTO(id, name, description, price, quantity, isActive, createdAt);

        // Then
        assertAll("ProductDTO fields",
                () -> assertEquals(id, dto.id()),
                () -> assertEquals(name, dto.name()),
                () -> assertEquals(description, dto.description()),
                () -> assertEquals(price, dto.price()),
                () -> assertEquals(quantity, dto.quantity()),
                () -> assertEquals(isActive, dto.isActive()),
                () -> assertEquals(createdAt, dto.createdAt())
        );
    }

    @Test
    @DisplayName("Should correctly implement equals and hashCode")
    void shouldImplementEqualsAndHashCode() {
        // Given
        ProductDTO dto1 = new ProductDTO(1L,
                "A",
                "Desc",
                new BigDecimal("10.00"),
                2L,
                true,
                LocalDateTime.of(2025, 6, 8, 0, 0, 0));
        ProductDTO dto2 = new ProductDTO(
                1L,
                "A",
                "Desc",
                new BigDecimal("10.00"),
                2L,
                true,
                LocalDateTime.of(2025, 6, 8, 0, 0, 0));
        ProductDTO dto3 = new ProductDTO(
                2L,
                "B",
                "Other",
                new BigDecimal("5.00"),
                1L,
                false,
                LocalDateTime.of(2025, 6, 7, 23, 59, 0));

        // Then
        assertAll("equals and hashCode",
                () -> assertEquals(dto1, dto2, "DTOs with same values should be equal"),
                () -> assertEquals(dto1.hashCode(), dto2.hashCode(), "Hash codes should match for equal objects"),
                () -> assertNotEquals(dto1, dto3, "DTOs with different values should not be equal")
        );
    }

    @Test
    @DisplayName("toString should contain all field values")
    void toStringShouldContainAllFields() {
        // Given
        ProductDTO dto = new ProductDTO(
                42L,
                "XYZ",
                "DescXYZ",
                new BigDecimal("99.99"),
                10L,
                false,
                LocalDateTime.of(2025, 6, 1, 8, 30, 0));

        // When
        String str = dto.toString();

        // Then
        assertAll("toString",
                () -> assertTrue(str.contains("42")),
                () -> assertTrue(str.contains("XYZ")),
                () -> assertTrue(str.contains("DescXYZ")),
                () -> assertTrue(str.contains("99.99")),
                () -> assertTrue(str.contains("10")),
                () -> assertTrue(str.contains("false")),
                () -> assertTrue(str.contains("2025-06-01T08:30"))
        );
    }
}
