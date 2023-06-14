package com.spring6.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Value not found!")
public class ProductImageAlreaydFoundException extends RuntimeException {
    public ProductImageAlreaydFoundException() {
    }

    public ProductImageAlreaydFoundException(String message) {
        super(message);
    }

    public ProductImageAlreaydFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductImageAlreaydFoundException(Throwable cause) {
        super(cause);
    }

    public ProductImageAlreaydFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

