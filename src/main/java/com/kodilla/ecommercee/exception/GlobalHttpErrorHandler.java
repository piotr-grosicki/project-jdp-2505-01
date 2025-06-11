package com.kodilla.ecommercee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException ex) {
        return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
    }
  
    @ExceptionHandler(GroupNotFoundException.class)
    public ResponseEntity<String> handleGroupNotFoundException(GroupNotFoundException ex) {
        return new ResponseEntity<>("Group with provided id doesn't exist.", HttpStatus.NOT_FOUND);
    }
   
   @ExceptionHandler(UserNotFoundByIdException.class)
    public ResponseEntity<String> handleUserNotFoundById(UserNotFoundByIdException ex) {
        return new ResponseEntity<>("User with provided id doesn't exist.", HttpStatus.NOT_FOUND);
    }
   
   @ExceptionHandler(UserNotFoundByMailException.class)
    public ResponseEntity<String> handleUserNotFoundByMail(UserNotFoundByMailException ex) {
        return new ResponseEntity<>("User with provided email doesn't exist.", HttpStatus.NOT_FOUND);
    }
}
