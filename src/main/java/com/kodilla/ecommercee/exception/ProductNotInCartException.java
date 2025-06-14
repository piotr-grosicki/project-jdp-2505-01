package com.kodilla.ecommercee.exception;

public class ProductNotInCartException extends Exception {
    public ProductNotInCartException(Long id) {
        super(" " + id);
    }
}
