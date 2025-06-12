package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.*;
import com.kodilla.ecommercee.dto.OrderDTO;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class OrderMapperTest {

    private final OrderMapper mapper = new OrderMapper();
    private final LocalDateTime now = LocalDateTime.of(2025, 1, 1, 12, 0);

    @Test
    void shouldMapEntityToDtoIncludingCartAndUser() {
        // given
        Order order = Order.builder()
                .id(100L)
                .status(OrderStatusEnum.PROCESSING)
                .totalAmount(BigDecimal.valueOf(250))
                .createdAt(now)
                .user(User.builder().id(5L).build())
                .cart(Cart.builder().id(7L).build())
                .products(List.of(Product.builder()
                        .id(9L)
                        .name("Prod")
                        .description("Desc")
                        .price(BigDecimal.ONE)
                        .build()))
                .build();

        // when
        OrderDTO dto = mapper.toDto(order);

        // then
        assertThat(dto.id()).isEqualTo(100L);
        assertThat(dto.status()).isEqualTo("PROCESSING");
        assertThat(dto.totalAmount()).isEqualByComparingTo("250");
        assertThat(dto.createdAt()).isEqualTo(now);
        assertThat(dto.userId()).isEqualTo(5L);
        assertThat(dto.cartId()).isEqualTo(7L);
        assertThat(dto.productIds()).containsExactly(9L);
    }

    @Test
    void shouldMapDtoToEntityRetainingBasicFields() {
        // given
        OrderDTO dto = new OrderDTO(200L, "COMPLETED", BigDecimal.valueOf(500), now, 11L, 13L, List.of(15L));

        // when
        Order order = mapper.toEntity(dto);

        // then
        assertThat(order.getId()).isEqualTo(200L);
        assertThat(order.getStatus()).isEqualTo(OrderStatusEnum.COMPLETED);
        assertThat(order.getTotalAmount()).isEqualByComparingTo("500");
        assertThat(order.getCreatedAt()).isEqualTo(now);
    }
}