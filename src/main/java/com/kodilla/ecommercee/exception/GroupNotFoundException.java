package com.kodilla.ecommercee.exception;

public class GroupNotFoundException extends Exception {
    public GroupNotFoundException(Long id) {
        super(" " + id);
    }
}
