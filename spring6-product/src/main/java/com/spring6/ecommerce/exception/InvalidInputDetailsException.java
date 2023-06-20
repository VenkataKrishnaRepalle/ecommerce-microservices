package com.spring6.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Value already found!")
public class InvalidInputDetailsException extends RuntimeException {
    public InvalidInputDetailsException() {
    }

    public InvalidInputDetailsException(String message) {
        super(message);
    }

    public InvalidInputDetailsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidInputDetailsException(Throwable cause) {
        super(cause);
    }

    public InvalidInputDetailsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
