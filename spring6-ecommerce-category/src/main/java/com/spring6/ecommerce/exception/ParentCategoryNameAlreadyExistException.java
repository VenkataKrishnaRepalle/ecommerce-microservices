package com.spring6.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Category Name already exist")
public class ParentCategoryNameAlreadyExistException extends RuntimeException {
    public ParentCategoryNameAlreadyExistException() {
    }

    public ParentCategoryNameAlreadyExistException(String message) {
        super(message);
    }

    public ParentCategoryNameAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParentCategoryNameAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public ParentCategoryNameAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
