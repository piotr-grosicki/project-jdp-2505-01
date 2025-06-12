package com.kodilla.ecommercee.exception;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(Long id) {
        super(" " + id);
    }
}
