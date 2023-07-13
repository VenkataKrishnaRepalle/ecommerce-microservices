package com.spring6.auth.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
    private final String errorCode;
    private final String dynamicValue;

    public UserNotFoundException(String errorCode, String dynamicValue) {
        super(String.format("%s : %s", errorCode, dynamicValue));

        this.errorCode = errorCode;
        this.dynamicValue = dynamicValue;
    }

}
