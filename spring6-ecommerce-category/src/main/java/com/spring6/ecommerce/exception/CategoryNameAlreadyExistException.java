package com.spring6.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Category Name already exist")
public class CategoryNameAlreadyExistException extends RuntimeException {
    public CategoryNameAlreadyExistException() {
    }

    public CategoryNameAlreadyExistException(String message) {
        super(message);
    }

    public CategoryNameAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryNameAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public CategoryNameAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
