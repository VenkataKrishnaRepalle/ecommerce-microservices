package com.pm.spring.ema.common.util.exception;

import com.pm.spring.ema.common.util.api.ErrorResponse;
import com.pm.spring.ema.common.util.api.FieldErrorListResponse;
import com.pm.spring.ema.common.util.exception.utils.ErrorCodes;
import com.pm.spring.ema.common.util.exception.utils.ErrorField;
import com.pm.spring.ema.common.util.exception.utils.ErrorMessage;
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

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(FieldErrorListResponse.builder().errors(errors).build());
    }

    @ExceptionHandler(FeignClientException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyFoundException(FeignClientException exception) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(ErrorMessage.errorResponse(exception.getErrorCode(), exception.getDynamicValue()));
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handleInvalidInputException(InvalidInputException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorMessage.errorResponse(exception.getErrorCode(), exception.getMessage()));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorMessage.errorResponse(ErrorCodes.E0500, exception.getMessage()));
    }

    @ExceptionHandler(FoundException.class)
    public ResponseEntity<ErrorResponse> handleFoundException(FoundException exception) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(ErrorMessage.errorResponse(exception.getErrorCode(), exception.getErrorMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorMessage.errorResponse(exception.getErrorCode(), exception.getErrorMessage()));
    }
}