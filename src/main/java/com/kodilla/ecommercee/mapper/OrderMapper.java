package com.kodilla.ecommercee.mapper;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.OrderStatusEnum;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.dto.OrderDTO;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderDTO toDto(Order order) {
        if (order == null) return null;
        return new OrderDTO(
                order.getId(),
                order.getStatus().name(),
                order.getTotalAmount(),
                order.getCreatedAt(),
                order.getUser() != null ? order.getUser().getId() : null,
                order.getCart() != null ? order.getCart().getId() : null,
                order.getProducts() != null ?
                        order.getProducts().stream().map(Product::getId).toList() : Collections.emptyList()
        );
    }

    public Order toEntity(OrderDTO dto) {
        if (dto == null) return null;
        return Order.builder()
                .id(dto.id())
                .status(dto.status() != null ? OrderStatusEnum.valueOf(dto.status()) : null)
                .totalAmount(dto.totalAmount())
                .createdAt(dto.createdAt())
                .products(Collections.emptyList())
                .build();
    }

    public List<OrderDTO> mapToDtoList(List<Order> orders) {
        return orders.stream().map(this::toDto).collect(Collectors.toList());
    }
}
