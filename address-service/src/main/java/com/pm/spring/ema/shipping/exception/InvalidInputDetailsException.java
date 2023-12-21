package com.pm.spring.ema.shipping.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Value already found!")
public class InvalidInputDetailsException extends RuntimeException {
    private final String errorCode;
    private final String dynamicValue;

    public InvalidInputDetailsException(String errorCode, String dynamicValue) {
        super(String.format("%s : %s", errorCode, dynamicValue));
        this.errorCode = errorCode;
        this.dynamicValue = dynamicValue;
    }

}
