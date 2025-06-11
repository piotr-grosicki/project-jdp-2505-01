package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.OrderStatusEnum;
import com.kodilla.ecommercee.exception.OrderNotFoundException;
import com.kodilla.ecommercee.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByStatus(OrderStatusEnum status) {
        return orderRepository.findAllByStatus(status).orElse(List.of());
    }

    public Order getOrder(Long id) throws OrderNotFoundException {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public Order createOrder(Order order) {
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatusEnum.NEW);
        return orderRepository.save(order);
    }

    public Order updateOrder(Order order) throws OrderNotFoundException {
        Order existing = orderRepository.findById(order.getId())
                .orElseThrow(() -> new OrderNotFoundException(order.getId()));
        existing.setStatus(order.getStatus());
        existing.setTotalAmount(order.getTotalAmount());
        existing.setProducts(order.getProducts());
        existing.setUser(order.getUser());
        existing.setCart(order.getCart());
        return orderRepository.save(existing);
    }

    public void deleteOrder(Long id) throws OrderNotFoundException {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException(id);
        }
        orderRepository.deleteById(id);
    }
}