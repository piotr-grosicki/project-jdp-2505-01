package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.OrderStatusEnum;
import com.kodilla.ecommercee.dto.OrderDTO;
import com.kodilla.ecommercee.exception.OrderNotFoundByIdException;
import com.kodilla.ecommercee.mapper.OrderMapper;
import com.kodilla.ecommercee.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orderMapper.mapToDtoList(orders));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderDTO>> getOrdersByStatus(@PathVariable OrderStatusEnum status) {
        List<Order> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orderMapper.mapToDtoList(orders));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long orderId) throws OrderNotFoundByIdException {
        Order order = orderService.getOrder(orderId);
        return ResponseEntity.ok(orderMapper.toDto(order));
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO dto) {
        Order order = orderMapper.toEntity(dto);
        Order created = orderService.createOrder(order);
        return ResponseEntity.ok(orderMapper.toDto(created));
    }

    @PutMapping
    public ResponseEntity<OrderDTO> updateOrder(@RequestBody OrderDTO dto) throws OrderNotFoundByIdException {
        Order order = orderMapper.toEntity(dto);
        Order updated = orderService.updateOrder(order);
        return ResponseEntity.ok(orderMapper.toDto(updated));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) throws OrderNotFoundByIdException {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok().build();
    }
}
