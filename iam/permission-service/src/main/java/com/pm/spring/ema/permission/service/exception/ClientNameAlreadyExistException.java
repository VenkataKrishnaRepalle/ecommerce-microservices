package com.pm.spring.ema.permission.service.exception;

import lombok.Getter;

@Getter
public class ClientNameAlreadyExistException extends RuntimeException {
    private final String errorCode;
    private final String dynamicValue;

    public ClientNameAlreadyExistException(String errorCode, String dynamicValue) {
        super(String.format("%s : %s", errorCode, dynamicValue));

        this.errorCode = errorCode;
        this.dynamicValue = dynamicValue;
    }
}
