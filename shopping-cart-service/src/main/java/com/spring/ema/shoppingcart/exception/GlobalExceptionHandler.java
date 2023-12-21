package com.spring.ema.shoppingcart.exception;

import com.pm.spring.ema.common.util.GlobalConstants;
import com.pm.spring.ema.common.util.api.ErrorResponse;
import com.pm.spring.ema.common.util.api.FieldErrorListResponse;
import com.pm.spring.ema.common.util.exception.ErrorCodes;
import com.pm.spring.ema.common.util.exception.ErrorField;
import com.pm.spring.ema.common.util.exception.ErrorMessage;
import com.spring.ema.shoppingcart.exception.shoppingCartException.ShoppingCartAlreadyExistException;
import com.spring.ema.shoppingcart.exception.shoppingCartException.ShoppingCartNotFoundException;
import com.spring.ema.shoppingcart.utils.TraceIdHolder;
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
public class GlobalExceptionHandler {

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

    @ExceptionHandler(ShoppingCartNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBrandNotFoundException(ShoppingCartNotFoundException exception) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .headers(headers)
                .body(ErrorMessage.errorResponse(exception.getErrorCode(), exception.getDynamicValue()));
    }

    @ExceptionHandler(ShoppingCartAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleBrandNameAlreadyExistException(ShoppingCartAlreadyExistException exception) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .headers(headers)
                .body(ErrorMessage.errorResponse(exception.getErrorCode(), exception.getDynamicValue()));
    }
//    @ExceptionHandler(SubCategoryNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleBrandNotFoundException(SubCategoryNotFoundException exception) {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());
//
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .headers(headers)
//                .body(ErrorMessage.errorResponse(exception.getErrorCode(), exception.getDynamicValue()));
//    }
//
//    @ExceptionHandler(SubCategoryNameAlreadyExistException.class)
//    public ResponseEntity<ErrorResponse> handleBrandNameAlreadyExistException(SubCategoryNameAlreadyExistException exception) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());
//        return ResponseEntity.status(HttpStatus.CONFLICT)
//                .headers(headers)
//                .body(ErrorMessage.errorResponse(exception.getErrorCode(), exception.getDynamicValue()));
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .headers(headers)
                .body(ErrorMessage.errorResponse(ErrorCodes.E0500, exception.getMessage()));
    }
}
