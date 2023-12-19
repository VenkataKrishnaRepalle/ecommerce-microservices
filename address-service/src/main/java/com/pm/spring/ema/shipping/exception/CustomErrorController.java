package com.pm.spring.ema.shipping.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomErrorController {

    @ExceptionHandler
    ResponseEntity handleJPAViolations(TransactionSystemException exception) {
        ResponseEntity.BodyBuilder responseEntity = ResponseEntity.badRequest();

        if(exception.getCause().getCause() instanceof ConstraintViolationException){
            ConstraintViolationException ve = (ConstraintViolationException) exception.getCause().getCause();

            List errors = ve.getConstraintViolations().stream()
                    .map(ConstraintViolation -> {
                        Map<String, String> errMap = new HashMap<>();
                        errMap.put(ConstraintViolation.getPropertyPath().toString(),
                                ConstraintViolation.getMessage());
                        return errMap;
                    }).collect(Collectors.toList());
            return responseEntity.body(errors);
        }

        return responseEntity.build();
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> handleBindErrors(MethodArgumentNotValidException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errors(exception.getFieldErrors().stream()
                        .map(fieldError -> ErrorFormat.builder()
                                .code(fieldError.getField())
                                .message(fieldError.getDefaultMessage())
                                .build())
                        .collect(Collectors.toList()))
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

