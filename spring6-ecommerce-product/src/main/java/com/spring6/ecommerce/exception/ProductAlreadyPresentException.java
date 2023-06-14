package com.spring6.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Value already found!")
public class ProductAlreadyPresentException extends RuntimeException {
    public ProductAlreadyPresentException() {
    }

    public ProductAlreadyPresentException(String message) {
        super(message);
    }

    public ProductAlreadyPresentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductAlreadyPresentException(Throwable cause) {
        super(cause);
    }

    public ProductAlreadyPresentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
