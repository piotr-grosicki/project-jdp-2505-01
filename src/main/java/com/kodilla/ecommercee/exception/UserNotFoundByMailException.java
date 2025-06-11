package com.kodilla.ecommercee.exception;

public class UserNotFoundByMailException extends Exception{
    public UserNotFoundByMailException(String email) {
        super("User not found: " + email);
    }
}
