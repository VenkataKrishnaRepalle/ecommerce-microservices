package com.spring6.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomErrorController {

    @ExceptionHandler(AlreadyFoundException.class)
    ResponseEntity handleCustomerAlreadyPresentExceptionError(AlreadyFoundException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NotFoundException.class)
    ResponseEntity handleCustomerNotFoundExceptionError(NotFoundException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }}
