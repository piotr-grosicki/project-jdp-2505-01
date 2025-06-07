package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.OrderStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void clearDatabase() {
        orderRepository.deleteAll();
    }

    private Order buildOrder(OrderStatusEnum status) {
        return Order.builder()
                .status(status)
                .totalAmount(BigDecimal.valueOf(500.75))
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testSaveOrder() {
        // Given
        Order order = buildOrder(OrderStatusEnum.NEW);

        // When
        Order savedOrder = orderRepository.save(order);

        // Then
        assertNotNull(savedOrder.getId());
        assertEquals(OrderStatusEnum.NEW, savedOrder.getStatus());
        assertEquals(BigDecimal.valueOf(500.75), savedOrder.getTotalAmount());

    }

    @Test
    void testFindOrderById() {
        // Given
        Order order = buildOrder(OrderStatusEnum.NEW);
        Order savedOrder = orderRepository.save(order);

        // When
        Optional<Order> foundOrder = orderRepository.findById(savedOrder.getId());

        // Then
        assertTrue(foundOrder.isPresent());
        assertEquals(savedOrder.getId(), foundOrder.get().getId());
        assertEquals(OrderStatusEnum.NEW, foundOrder.get().getStatus());

    }

    @Test
    void testFindOrdersByStatus() {
        // Given
        Order order1 = buildOrder(OrderStatusEnum.NEW);
        Order order2 = buildOrder(OrderStatusEnum.NEW);
        Order order3 = buildOrder(OrderStatusEnum.CANCELLED);
        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);
        // When
        Optional<List<Order>> foundOrders = orderRepository.findAllByStatus(OrderStatusEnum.NEW);
        // Then
        assertTrue(foundOrders.isPresent());
        List<Order> orders = foundOrders.get();
        assertEquals(2, orders.size());
        assertTrue(orders.stream().allMatch(order -> order.getStatus() == OrderStatusEnum.NEW));
    }

    @Test
    void testDeleteFindById() {
        // Given
        Order order = buildOrder(OrderStatusEnum.NEW);
        Order savedOrder = orderRepository.save(order);
        // When
        Long orderId = savedOrder.getId();
        orderRepository.delete(savedOrder);
        Optional<Order> foundOrder = orderRepository.findById(orderId);
        // Then
        assertFalse(foundOrder.isPresent());
    }
}