package com.spring6.order.exception;

import brave.Tracer;
import com.pm.spring.ema.common.util.api.ErrorResponse;
import com.pm.spring.ema.common.util.api.FieldErrorListResponse;
import com.pm.spring.ema.common.util.exception.ErrorCodes;
import com.pm.spring.ema.common.util.exception.ErrorField;
import com.pm.spring.ema.common.util.exception.ErrorMessage;
import com.pm.spring.ema.common.util.GlobalConstants;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class GlobalOrderExceptionHandler {

    private final Tracer tracer;

    @ExceptionHandler(BindException.class)
    public ResponseEntity<FieldErrorListResponse> handleValidationException(BindException bindException) {
        BindingResult bindingResult = bindException.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        List<ErrorField> errors = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            String[] errorCodeAndMessage = fieldError.getDefaultMessage().split("-", 1);
            String errorCode = errorCodeAndMessage[0];
            String errorMessage = errorCodeAndMessage[1];

            errors.add(ErrorField.builder()
                    .code(errorCode)
                    .message(MessageFormat.format(errorMessage, fieldError.getField()))
                    .fieldName(fieldError.getField())
                    .build());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, tracer.currentSpan().context().traceIdString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .headers(headers)
                .body(FieldErrorListResponse.builder().errors(errors).build());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBrandNotFoundException(OrderNotFoundException exception) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, tracer.currentSpan().context().traceIdString());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .headers(headers)
                .body(ErrorMessage.errorResponse(exception.getErrorCode(), exception.getDynamicValue()));
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handleInvalidInputException(InvalidInputException exception) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, tracer.currentSpan().context().traceIdString());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .headers(headers)
                .body(ErrorMessage.errorResponse(exception.getErrorCode()));
    }


    @ExceptionHandler(OrderQuantityException.class)
    public ResponseEntity<ErrorResponse> handleOrderQuantityException(OrderQuantityException exception) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, tracer.currentSpan().context().traceIdString());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .headers(headers)
                .body(ErrorMessage.errorResponse(exception.getErrorCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, tracer.currentSpan().context().traceIdString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .headers(headers)
                .body(ErrorMessage.errorResponse(ErrorCodes.E0500, exception.getMessage()));
    }
}