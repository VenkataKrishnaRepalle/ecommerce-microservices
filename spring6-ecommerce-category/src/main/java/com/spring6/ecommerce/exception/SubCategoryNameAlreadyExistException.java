package com.spring6.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Category Name already exist")
public class SubCategoryNameAlreadyExistException extends RuntimeException {
    public SubCategoryNameAlreadyExistException() {
    }

    public SubCategoryNameAlreadyExistException(String message) {
        super(message);
    }

    public SubCategoryNameAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public SubCategoryNameAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public SubCategoryNameAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
