package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.OrderStatusEnum;
import com.kodilla.ecommercee.exception.OrderNotFoundException;
import com.kodilla.ecommercee.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository repo;
    @InjectMocks
    private OrderService service;

    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        order = Order.builder()
                .id(1L)
                .createdAt(LocalDateTime.now())
                .status(null)
                .totalAmount(BigDecimal.TEN)
                .build();
    }

    @Test
    void shouldCreateOrderAndSetDefaults() {
        when(repo.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));
        Order created = service.createOrder(order);
        assertThat(created.getStatus()).isEqualTo(OrderStatusEnum.NEW);
        assertThat(created.getCreatedAt()).isNotNull();
    }

    @Test
    void shouldThrowWhenGettingNonExisting() {
        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getOrder(2L))
                .isInstanceOf(OrderNotFoundException.class);
    }

    @Test
    void shouldReturnAllOrders() {
        when(repo.findAll()).thenReturn(List.of(order));
        List<Order> list = service.getAllOrders();
        assertThat(list).hasSize(1);
    }

    @Test
    void shouldReturnByStatus() {
        when(repo.findAllByStatus(OrderStatusEnum.NEW)).thenReturn(Optional.of(List.of(order)));
        List<Order> list = service.getOrdersByStatus(OrderStatusEnum.NEW);
        assertThat(list).contains(order);
    }

    @Test
    void shouldUpdateExistingOrder() throws OrderNotFoundException {
        Order updated = Order.builder().id(1L).status(OrderStatusEnum.CANCELLED).build();
        when(repo.findById(1L)).thenReturn(Optional.of(order));
        when(repo.save(any(Order.class))).thenReturn(updated);
        Order result = service.updateOrder(updated);
        assertThat(result.getStatus()).isEqualTo(OrderStatusEnum.CANCELLED);
    }

    @Test
    void shouldDeleteOrder() throws OrderNotFoundException {
        when(repo.existsById(1L)).thenReturn(true);
        service.deleteOrder(1L);
        verify(repo).deleteById(1L);
    }
}