package com.kodilla.ecommercee.exception;

public class OrderNotFoundException extends Exception {
    public OrderNotFoundException(Long id) {
        super(" " + id);
    }
}
