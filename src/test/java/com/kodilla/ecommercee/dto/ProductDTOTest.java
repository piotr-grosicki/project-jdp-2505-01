package com.kodilla.ecommercee.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductDTOTest {

    @Test
    @DisplayName("Should create ProductDTO and access all fields correctly")
    void shouldCreateProductDTOAndAccessFields() {
        // Given
        Long id = 1L;
        String name = "Test Product";
        String description = "Description of test product";
        String price = "19.99";
        String quantity = "5";
        String status = "AVAILABLE";
        String createdAt = "2025-06-08T12:00:00";

        // When
        ProductDTO dto = new ProductDTO(id, name, description, price, quantity, status, createdAt);

        // Then
        assertAll("ProductDTO fields",
                () -> assertEquals(id, dto.id()),
                () -> assertEquals(name, dto.name()),
                () -> assertEquals(description, dto.description()),
                () -> assertEquals(price, dto.price()),
                () -> assertEquals(quantity, dto.quantity()),
                () -> assertEquals(status, dto.status()),
                () -> assertEquals(createdAt, dto.createdAt())
        );
    }

    @Test
    @DisplayName("Should correctly implement equals and hashCode")
    void shouldImplementEqualsAndHashCode() {
        // Given
        ProductDTO dto1 = new ProductDTO(1L, "A", "Desc", "10.00", "2", "NEW", "2025-06-08T00:00:00");
        ProductDTO dto2 = new ProductDTO(1L, "A", "Desc", "10.00", "2", "NEW", "2025-06-08T00:00:00");
        ProductDTO dto3 = new ProductDTO(2L, "B", "Other", "5.00", "1", "OUT_OF_STOCK", "2025-06-07T23:59:59");

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
        ProductDTO dto = new ProductDTO(42L, "XYZ", "DescXYZ", "99.99", "10", "DISCONTINUED", "2025-06-01T08:30:00");

        // When
        String str = dto.toString();

        // Then
        assertAll("toString",
                () -> assertTrue(str.contains("42")),
                () -> assertTrue(str.contains("XYZ")),
                () -> assertTrue(str.contains("DescXYZ")),
                () -> assertTrue(str.contains("99.99")),
                () -> assertTrue(str.contains("10")),
                () -> assertTrue(str.contains("DISCONTINUED")),
                () -> assertTrue(str.contains("2025-06-01T08:30:00"))
        );
    }
}
