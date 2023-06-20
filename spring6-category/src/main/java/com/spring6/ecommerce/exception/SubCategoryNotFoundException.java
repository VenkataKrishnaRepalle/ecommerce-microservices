package com.spring6.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Value not found!")
public class SubCategoryNotFoundException extends RuntimeException {
    public SubCategoryNotFoundException() {
    }

    public SubCategoryNotFoundException(String message) {
        super(message);
    }

    public SubCategoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SubCategoryNotFoundException(Throwable cause) {
        super(cause);
    }

    public SubCategoryNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
