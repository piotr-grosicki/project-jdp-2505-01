package com.kodilla.ecommercee.exception;

public class GroupNotFoundByIdException extends Exception {
    public GroupNotFoundByIdException(Long id) {
        super(" " + id);
    }
}
