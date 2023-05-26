package com.spring6.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Brand Name already exist")
public class UserNameAlreadyExistException extends RuntimeException {
    public UserNameAlreadyExistException() {
    }

    public UserNameAlreadyExistException(String message) {
        super(message);
    }

    public UserNameAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNameAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public UserNameAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
