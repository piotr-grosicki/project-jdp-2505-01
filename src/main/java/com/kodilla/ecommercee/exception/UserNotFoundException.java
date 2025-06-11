package com.kodilla.ecommercee.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(Long id) {
        super("User not found: " + id);
    }
}
