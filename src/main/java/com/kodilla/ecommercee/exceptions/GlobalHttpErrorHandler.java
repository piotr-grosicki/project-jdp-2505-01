package com.kodilla.ecommercee.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Object> handleBookNotFoundException(CartNotFoundException ex) {
        return new ResponseEntity<>("Cart with given id doesn't exist", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException ex) {
        return new ResponseEntity<>("Product with given id doesn't exist", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotInCartException.class)
    public ResponseEntity<Object> handleProductNotInCartException(ProductNotInCartException ex) {
        return new ResponseEntity<>("Product is not in the cart", HttpStatus.BAD_REQUEST);
    }
}
