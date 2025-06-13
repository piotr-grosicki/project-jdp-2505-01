package com.kodilla.ecommercee.exception;

public class OrderNotFoundByIdException extends Exception {
    public OrderNotFoundByIdException(Long id) {
        super(" " + id);
    }
}
