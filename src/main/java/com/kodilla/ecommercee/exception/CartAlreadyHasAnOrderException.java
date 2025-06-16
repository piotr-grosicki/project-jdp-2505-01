package com.kodilla.ecommercee.exception;

public class CartAlreadyHasAnOrderException extends Exception {
    public CartAlreadyHasAnOrderException(String message) {
        super(message);
    }
}
