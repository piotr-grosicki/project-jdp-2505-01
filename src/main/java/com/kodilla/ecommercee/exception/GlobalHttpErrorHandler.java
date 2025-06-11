package com.kodilla.ecommercee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler {

    @ExceptionHandler(UserNotFoundByIdException.class)
    public ResponseEntity<String> handleUserNotFoundById(UserNotFoundByIdException ex) {
        return new ResponseEntity<>("User with provided id doesn't exist.", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UserNotFoundByMailException.class)
    public ResponseEntity<String> handleUserNotFoundByMail(Exception ex) {
        return new ResponseEntity<>("User with provided email doesn't exist.", HttpStatus.NOT_FOUND);
    }

}