package com.spring6.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageSource messageSource;


    @ExceptionHandler(BindException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(BindException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
        errorResponse.setMessage("Validation failed");

        List<ErrorResponse> errorResponses = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            String errorMessage = messageSource.getMessage(fieldError.getDefaultMessage(), new Object[]{fieldError.getField()}, LocaleContextHolder.getLocale());

            errorResponses.add(ErrorResponse.builder()
                    .errorCode(fieldError.getDefaultMessage())
                    .errorMessage(fieldError.getDefaultMessage())
                    .build());
        }
        errorResponse.setErrors(errorResponses);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        String errorMessage = messageSource.getMessage("error.general", null, LocaleContextHolder.getLocale());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder()
                .errorCode("GENERAL_ERROR")
                .errorMessage(errorMessage)
                .build()
        );
    }
}
