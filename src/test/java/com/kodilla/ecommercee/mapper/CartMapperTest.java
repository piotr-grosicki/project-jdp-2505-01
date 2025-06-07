package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.CartStatusEnum;
import com.kodilla.ecommercee.dto.CartDTO;
import com.kodilla.ecommercee.dto.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartMapperTest {

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private CartMapper cartMapper;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldMapToCartDto() {
        // Given
        Cart cart = Cart.builder()
                .id(10L)
                .status(CartStatusEnum.CREATED)
                .createdAt(LocalDateTime.of(2025, 6, 7, 14, 0))
                .products(List.of())
                .build();
        List<ProductDTO> dtoList = List.of(
                new ProductDTO(1L, "prod",
                        "desc", "9.99", "20", "CREATED", "2025-06-07T14:00"
                ));
        when(productMapper.mapToProductDTOList(cart.getProducts())).thenReturn(dtoList);

        // When
        CartDTO result = cartMapper.mapToCartDto(cart);

        // Then
        assertEquals(10L, result.id());
        assertEquals("CREATED", result.status());
        assertEquals("2025-06-07T14:00", result.createdAt());
        assertEquals(dtoList, result.products());
        verify(productMapper, times(1)).mapToProductDTOList(cart.getProducts());
    }

    @Test
    void shouldMapToCartDtoWithNulls() {
        // Given
        Cart cart = Cart.builder().build();
        when(productMapper.mapToProductDTOList(null)).thenReturn(null);

        // When
        CartDTO result = cartMapper.mapToCartDto(cart);

        // Then
        assertNull(result.id());
        assertNull(result.status());
        assertNull(result.createdAt());
        assertNull(result.products());
    }

    @Test
    void shouldMapToCart() {
        // Given
        CartDTO dto = new CartDTO(5L, "COMPLETED", "2025-06-07T16:30:00", null);

        // When
        Cart result = cartMapper.mapToCart(dto);

        // Then
        assertNull(result.getId()); // builder doesn't set id
        assertEquals(CartStatusEnum.COMPLETED, result.getStatus());
        assertEquals(LocalDateTime.of(2025, 6, 7, 16, 30), result.getCreatedAt());
    }

    @Test
    void shouldMapToCartWithNulls() {
        // Given
        CartDTO dto = new CartDTO(null, null, null, List.of());

        // When
        Cart result = cartMapper.mapToCart(dto);

        // Then
        assertNull(result.getStatus());
        assertNull(result.getCreatedAt());
    }
}