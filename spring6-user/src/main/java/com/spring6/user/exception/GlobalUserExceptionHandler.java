package com.spring6.user.exception;

import com.spring6.common.exeption.Error;
import com.spring6.common.exeption.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalUserExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorListResponse> handleValidationException(BindException bindException) {
        BindingResult bindingResult = bindException.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        List<Error> errors = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {

            errors.add(Error.builder()
                    .code(fieldError.getDefaultMessage())
                    .message(MessageFormat.format(ErrorCodes.valueOf(fieldError.getDefaultMessage()).getMessage(), fieldError.getField()))
                    .build());
        }

        ErrorListResponse errorResponse = ErrorListResponse.builder()
                .errors(errors)
                .build();

        errorResponse.setErrors(errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBrandNotFoundException(UserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorMessage.errorResponse(exception.getErrorCode(), exception.getDynamicValue()));
    }

    @ExceptionHandler(UserNameAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleBrandNameAlreadyExistException(UserNameAlreadyExistException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorMessage.errorResponse(exception.getErrorCode(), exception.getDynamicValue()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorMessage.errorResponse(ErrorCodes.E0500.getCode(), exception.getMessage()));
    }
}
