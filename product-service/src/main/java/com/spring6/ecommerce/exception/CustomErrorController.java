package com.pm.spring.ema.ecommerce.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.DataTruncation;
import java.text.MessageFormat;
import java.util.*;
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
    @ExceptionHandler(ProductNotFoundException.class)
    ResponseEntity handleProductNotFoundExceptionError(ProductNotFoundException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errors(new ArrayList<>(Arrays.asList(
                        ErrorFormat.builder()
                                .code(exception.getErrorCode())
                                .message(MessageFormat.format(ErrorCode.valueOf(exception.getErrorCode()).getMessage(), exception.getDynamicValue()))
                                .build())))
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }


    @ExceptionHandler(InvalidInputDetailsException.class)
    ResponseEntity handleInvalidInputDetailsExceptionError(InvalidInputDetailsException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductAlreadyPresentException.class)
    ResponseEntity handleProductAlreadyPresentExceptionError(ProductAlreadyPresentException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity handleDataIntegrationViolationExceptionError(DataIntegrityViolationException exception) {
        return new ResponseEntity<>(exception.getRootCause().getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity handleHttpMessageNotReadableExceptionError(HttpMessageNotReadableException exception) {
        return  ResponseEntity.badRequest().body(exception.getRootCause().getLocalizedMessage());
    }

//    @ExceptionHandler(UnexpectedTypeException.class)
//    ResponseEntity handleUnexpectedExceptionError(UnexpectedTypeException exception) {
//        return ResponseEntity.badRequest().body(exception.getMessage());
//    }
}
