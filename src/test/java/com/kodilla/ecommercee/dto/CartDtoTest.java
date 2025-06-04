package com.kodilla.ecommercee.dto;

import com.kodilla.ecommercee.domain.CartStatusEnum;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CartDtoTest {

    @Test
    void creatingCartDtoWithConstructor() {
        // Given
        Long id = 1L;
        CartStatusEnum status = CartStatusEnum.COMPLETED;
        LocalDateTime createdAt = LocalDateTime.now();

        // When
        CartDto cartDto = new CartDto(id, status, createdAt);

        // Then
        assertThat(cartDto.getId()).isEqualTo(id);
        assertThat(cartDto.getStatus()).isEqualTo(status);
        assertThat(cartDto.getCreatedAt()).isEqualTo(createdAt);
    }

    @Test
    void creatingCartDtoWithBuilder() {
        // Given
        Long id = 2L;
        CartStatusEnum status = CartStatusEnum.CANCELLED;
        LocalDateTime createdAt = LocalDateTime.now();

        // When
        CartDto cartDto = CartDto.builder()
                .id(id)
                .status(status)
                .createdAt(createdAt)
                .build();

        // Then
        assertThat(cartDto.getId()).isEqualTo(id);
        assertThat(cartDto.getStatus()).isEqualTo(status);
        assertThat(cartDto.getCreatedAt()).isEqualTo(createdAt);
    }
}