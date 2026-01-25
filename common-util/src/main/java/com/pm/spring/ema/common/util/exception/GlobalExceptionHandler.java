package com.pm.spring.ema.common.util.exception;

import com.pm.spring.ema.common.util.api.ErrorResponse;
import com.pm.spring.ema.common.util.exception.utils.ErrorCodes;
import com.pm.spring.ema.common.util.exception.utils.ErrorMessage;
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
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<List<ErrorResponse>> handleValidationException(BindException bindException) {
        BindingResult bindingResult = bindException.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        List<ErrorResponse> errors = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            String error = fieldError.getDefaultMessage();

            errors.add(ErrorResponse.builder()
                            .message(error)
                            .build());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errors);
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