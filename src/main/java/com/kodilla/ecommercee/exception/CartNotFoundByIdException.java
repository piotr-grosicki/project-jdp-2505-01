package com.kodilla.ecommercee.exception;

public class CartNotFoundByIdException extends Exception {
    public CartNotFoundByIdException(Long id) {
        super(" " + id);
    }
}
