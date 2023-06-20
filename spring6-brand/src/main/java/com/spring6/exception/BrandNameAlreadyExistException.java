package com.spring6.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Brand Name already exist")
public class BrandNameAlreadyExistException extends RuntimeException {
    public BrandNameAlreadyExistException() {
    }

    public BrandNameAlreadyExistException(String message) {
        super(message);
    }

    public BrandNameAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public BrandNameAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public BrandNameAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
