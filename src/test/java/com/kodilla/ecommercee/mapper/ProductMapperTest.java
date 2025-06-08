package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.dto.ProductDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductMapperTest {

    @Autowired
    private ProductMapper mapper;

    @Test
    @DisplayName("Should map Product to ProductDTO correctly when active")
    void shouldMapProductToDTOActive() {
        // Given
        Product product = Product.builder()
                .id(10L)
                .name("ProdName")
                .description("ProdDesc")
                .price(new BigDecimal("123.45"))
                .stockQuantity(20L)
                .isActive(true)
                .createdAt(LocalDateTime.of(2025, 6, 8, 14, 30, 0))
                .build();

        // When
        ProductDTO dto = mapper.toProductDTO(product);

        // Then
        assertAll("DTO fields",
                () -> assertEquals(10L, dto.id()),
                () -> assertEquals("ProdName", dto.name()),
                () -> assertEquals("ProdDesc", dto.description()),
                () -> assertEquals("123.45", dto.price()),
                () -> assertEquals("20", dto.quantity()),
                () -> assertEquals("ACTIVE", dto.status()),
                () -> assertEquals("2025-06-08T14:30", dto.createdAt().substring(0,16))
        );
    }

    @Test
    @DisplayName("Should map Product to ProductDTO with INACTIVE status")
    void shouldMapProductToDTOInactive() {
        // Given
        Product product = Product.builder()
                .id(11L)
                .name("X")
                .description("Y")
                .price(new BigDecimal("0"))
                .stockQuantity(0L)
                .isActive(false)
                .createdAt(LocalDateTime.of(2025, 1, 1, 0, 0, 0))
                .build();

        // When
        ProductDTO dto = mapper.toProductDTO(product);

        // Then
        assertEquals("INACTIVE", dto.status());
    }

    @Test
    @DisplayName("Should map list of Products to list of ProductDTOs")
    void shouldMapListToDTOList() {
        // Given
        Product p1 = Product.builder()
                .id(1L)
                .name("A").description("D1")
                .price(new BigDecimal("1.00")).stockQuantity(1L)
                .isActive(true).createdAt(LocalDateTime.of(2025,5,1,10,0))
                .build();
        Product p2 = Product.builder()
                .id(2L)
                .name("B").description("D2")
                .price(new BigDecimal("2.00")).stockQuantity(2L)
                .isActive(false).createdAt(LocalDateTime.of(2025,5,2,11,0))
                .build();

        List<Product> products = Arrays.asList(p1, p2);

        // When
        List<ProductDTO> dtos = mapper.mapToProductDTOList(products);

        // Then
        assertEquals(2, dtos.size());
        assertEquals(p1.getId(), dtos.get(0).id());
        assertEquals(p2.getId(), dtos.get(1).id());
        assertEquals("ACTIVE", dtos.get(0).status());
        assertEquals("INACTIVE", dtos.get(1).status());
    }

    @Test
    @DisplayName("Should map ProductDTO to Product correctly when status ACTIVE")
    void shouldMapDTOToProductActive() {
        // Given
        ProductDTO dto = new ProductDTO(
                5L,
                "N",
                "Desc",
                "55.55",
                "7",
                "ACTIVE",
                "2025-06-08T19:00:00"
        );

        // When
        Product product = mapper.toProduct(dto);

        // Then
        assertAll("Product fields",
                () -> assertEquals(5L, product.getId()),
                () -> assertEquals("N", product.getName()),
                () -> assertEquals("Desc", product.getDescription()),
                () -> assertEquals(new BigDecimal("55.55"), product.getPrice()),
                () -> assertEquals(7L, product.getStockQuantity()),
                () -> assertTrue(product.isActive())
        );
    }

    @Test
    @DisplayName("Should map ProductDTO to Product with inactive when status lowercase")
    void shouldMapDTOToProductInactiveLowercase() {
        // Given
        ProductDTO dto = new ProductDTO(
                6L,
                "M",
                "D",
                "0.00",
                "0",
                "inactive",
                "2025-06-08T19:00:00"
        );

        // When
        Product product = mapper.toProduct(dto);

        // Then
        assertFalse(product.isActive());
    }

    @Test
    @DisplayName("Should throw NumberFormatException for invalid quantity or price")
    void shouldThrowForInvalidDTOValues() {
        // Given
        ProductDTO badPrice = new ProductDTO(1L,
                "A",
                "B",
                "abc",
                "1",
                "ACTIVE",
                "");
        ProductDTO badQuantity = new ProductDTO(1L,
                "A",
                "B",
                "1.00",
                "xyz",
                "ACTIVE",
                "");

        // Then
        assertThrows(NumberFormatException.class, () -> mapper.toProduct(badPrice));
        assertThrows(NumberFormatException.class, () -> mapper.toProduct(badQuantity));
    }
}
