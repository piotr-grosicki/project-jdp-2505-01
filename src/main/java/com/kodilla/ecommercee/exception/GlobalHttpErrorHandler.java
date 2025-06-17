package com.kodilla.ecommercee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundByIdException.class)
    public ResponseEntity<String> handleUserNotFoundById(UserNotFoundByIdException ex) {
        return new ResponseEntity<>("User with provided id:" + ex.getMessage()
                + " doesn't exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundByMailException.class)
    public ResponseEntity<String> handleUserNotFoundByMail(UserNotFoundByMailException ex) {
        return new ResponseEntity<>("User with provided email:" + ex.getMessage()
                + " doesn't exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundByIdException.class)
    public ResponseEntity<String> handleProductNotFoundByIdException(ProductNotFoundByIdException ex) {
        return new ResponseEntity<>("Product with provided id:" + ex.getMessage()
                + " doesn't exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GroupNotFoundByIdException.class)
    public ResponseEntity<String> handleGroupNotFoundByIdException(GroupNotFoundByIdException ex) {
        return new ResponseEntity<>("Group with provided id:" + ex.getMessage()
                + " doesn't exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundByIdException.class)
    public ResponseEntity<String> handleOrderNotFoundByIdException(OrderNotFoundByIdException ex) {
        return new ResponseEntity<>("Order with provided id: " + ex.getMessage()
                + " doesn't exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotInCartException.class)
    public ResponseEntity<String> handleProductNotInCartException(ProductNotInCartException ex) {
        return new ResponseEntity<>("Product with provided id: " + ex.getMessage()
                + " is not in the cart.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CartAlreadyHasAnOrderException.class)
    public ResponseEntity<String> handleCartAlreadyHasAnOrderException(CartAlreadyHasAnOrderException ex) {
        return new ResponseEntity<>("Cart with provided id: " + ex.getMessage()
                + " already has an order.", HttpStatus.BAD_REQUEST);
    }
}

