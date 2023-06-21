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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(BindException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        List<ErrorFormat> errors = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {

            errors.add(ErrorFormat.builder()
                    .code(fieldError.getDefaultMessage())
                    .message(MessageFormat.format(ErrorCode.valueOf(fieldError.getDefaultMessage()).getMessage(), fieldError.getField()))
                    .build());
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errors(errors)
                .build();

        errorResponse.setErrors(errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
//
//        String errorMessage = messageSource.getMessage("error.general", null, LocaleContextHolder.getLocale());
//
//        ErrorResponse errorResponse = ErrorResponse.builder()
//                .errors(new ArrayList<>(Arrays.asList(
//                        ErrorFormat.builder()
//                                .code("GENERAL_ERROR")
//                                .message(errorMessage)
//                                .build())))
//                .build();
//
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
//    }
}
