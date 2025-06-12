package com.kodilla.ecommercee.dto;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class OrderDTOTest {

    @Test
    void shouldCreateDtoProperly() {
        LocalDateTime now = LocalDateTime.now();
        OrderDTO dto = new OrderDTO(1L, "NEW", BigDecimal.valueOf(100.50), now, 2L, 3L, List.of(4L, 5L));

        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.status()).isEqualTo("NEW");
        assertThat(dto.totalAmount()).isEqualByComparingTo("100.50");
        assertThat(dto.createdAt()).isEqualTo(now);
        assertThat(dto.userId()).isEqualTo(2L);
        assertThat(dto.cartId()).isEqualTo(3L);
        assertThat(dto.productIds()).containsExactly(4L,5L);
    }
}