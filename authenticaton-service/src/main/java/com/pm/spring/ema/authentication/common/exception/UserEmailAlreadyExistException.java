package com.pm.spring.ema.authentication.common.exception;

import lombok.Getter;

@Getter
public class UserEmailAlreadyExistException extends RuntimeException {
    private final String errorCode;
    private final String dynamicValue;

    public UserEmailAlreadyExistException(String errorCode, String dynamicValue) {
        super(String.format("%s : %s", errorCode, dynamicValue));

        this.errorCode = errorCode;
        this.dynamicValue = dynamicValue;
    }
}
