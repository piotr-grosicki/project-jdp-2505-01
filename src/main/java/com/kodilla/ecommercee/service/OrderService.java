package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.OrderStatusEnum;
import com.kodilla.ecommercee.exception.OrderNotFoundByIdException;
import com.kodilla.ecommercee.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public Order getOrder(Long id) throws OrderNotFoundByIdException {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundByIdException(id));
    }

    public Order createOrder(Order order) {
        order.setStatus(OrderStatusEnum.NEW);
        return orderRepository.save(order);
    }

    public Order updateOrder(Order order) throws OrderNotFoundByIdException {
        Order existing = orderRepository.findById(order.getId())
                .orElseThrow(() -> new OrderNotFoundByIdException(order.getId()));
        existing.setStatus(order.getStatus());
        existing.setTotalAmount(order.getTotalAmount());
        existing.setProducts(order.getProducts());
        return orderRepository.save(existing);
    }

    public void deleteOrder(Long id) throws OrderNotFoundByIdException {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundByIdException(id);
        }
        orderRepository.deleteById(id);
    }
}