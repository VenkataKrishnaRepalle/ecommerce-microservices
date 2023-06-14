package com.spring6.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Value not found!")
public class parentCategoryNotFoundException extends RuntimeException {
    public parentCategoryNotFoundException() {
    }

    public parentCategoryNotFoundException(String message) {
        super(message);
    }

    public parentCategoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public parentCategoryNotFoundException(Throwable cause) {
        super(cause);
    }

    public parentCategoryNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
