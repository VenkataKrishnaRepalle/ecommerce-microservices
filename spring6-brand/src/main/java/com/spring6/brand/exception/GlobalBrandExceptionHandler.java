package com.spring6.brand.exception;

import com.spring6.brand.utils.TraceIdHolder;
import com.spring6.common.exeption.*;
import com.spring6.common.utils.GlobalConstants;
import org.springframework.http.HttpHeaders;
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
public class GlobalBrandExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<FieldErrorListResponse> handleValidationException(BindException bindException) {
        BindingResult bindingResult = bindException.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        List<ErrorField> errors = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            String[] errorCodeAndMessage = fieldError.getDefaultMessage().split("-");
            String errorCode = errorCodeAndMessage[0];
            String errorMessage = errorCodeAndMessage[1];

            errors.add(ErrorField.builder()
                    .code(errorCode)
                    .message(MessageFormat.format(errorMessage, fieldError.getField()))
                    .fieldName(fieldError.getField())
                    .build());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .headers(headers)
                .body(FieldErrorListResponse.builder().errors(errors).build());
    }

    @ExceptionHandler(BrandNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBrandNotFoundException(BrandNotFoundException exception) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .headers(headers)
                .body(ErrorMessage.errorResponse(exception.getErrorCode(), exception.getDynamicValue()));
    }

    @ExceptionHandler(BrandNameAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleBrandNameAlreadyExistException(BrandNameAlreadyExistException exception) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .headers(headers)
                .body(ErrorMessage.errorResponse(exception.getErrorCode(), exception.getDynamicValue()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .headers(headers)
                .body(ErrorMessage.errorResponse(ErrorCodes.E0500, exception.getMessage()));
    }
}
