package com.kodilla.ecommercee.exception;

public class ProductNotFoundByIdException extends Exception {
    public ProductNotFoundByIdException(Long id) {
        super(" " + id);
    }
}
