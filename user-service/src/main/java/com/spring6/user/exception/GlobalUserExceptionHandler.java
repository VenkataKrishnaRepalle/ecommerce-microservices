package com.pm.spring.ema.user.exception;

import com.pm.spring.ema.common.exeption.Error;
import com.pm.spring.ema.common.exeption.*;
import com.pm.spring.ema.common.utils.GlobalConstants;
import com.pm.spring.ema.user.filter.TraceIdHolder;
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
public class GlobalUserExceptionHandler {

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


    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorResponse> handleBrandNotFoundException(PasswordMismatchException exception) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        String[] errorCodeAndMessage = exception.getError().split("-");
        String errorCode = errorCodeAndMessage[0];
        String errorMessage = errorCodeAndMessage[1];

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .headers(headers)
                .body(ErrorResponse.builder()
                        .error(Error.builder()
                                .code(errorCode)
                                .message(errorMessage)
                                .build())
                        .build());
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBrandNotFoundException(UserNotFoundException exception) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .headers(headers)
                .body(ErrorMessage.errorResponse(exception.getErrorCode(), exception.getDynamicValue()));
    }

    @ExceptionHandler(UserPhotoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBrandNotFoundException(UserPhotoNotFoundException exception) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .headers(headers)
                .body(ErrorMessage.errorResponse(exception.getErrorCode(), exception.getDynamicValue()));
    }


    @ExceptionHandler(UserNameAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleBrandNameAlreadyExistException(UserNameAlreadyExistException exception) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .headers(headers)
                .body(ErrorMessage.errorResponse(exception.getErrorCode(), exception.getDynamicValue()));
    }

    @ExceptionHandler(UserEmailAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleBrandNameAlreadyExistException(UserEmailAlreadyExistException exception) {
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
