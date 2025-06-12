package com.kodilla.ecommercee.exception;

public class UserNotFoundByIdException extends Exception {

    public UserNotFoundByIdException(Long id) {
        super(" " + id);
    }
}
