package com.spring6.user.exception;

import lombok.Getter;

@Getter
public class UserNameAlreadyExistException extends RuntimeException {
    private final String errorCode;
    private final String dynamicValue;

    public UserNameAlreadyExistException(String errorCode, String dynamicValue) {
        super(String.format("%s : %s", errorCode, dynamicValue));

        this.errorCode = errorCode;
        this.dynamicValue = dynamicValue;
    }
}
