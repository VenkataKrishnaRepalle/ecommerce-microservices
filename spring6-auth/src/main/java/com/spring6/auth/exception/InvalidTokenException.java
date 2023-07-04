package com.spring6.auth.exception;

import lombok.Getter;

@Getter
public class InvalidTokenException extends RuntimeException {
    private final String errorCode;

    public InvalidTokenException(String errorCode) {
        super(String.format("%s : %s", errorCode));

        this.errorCode = errorCode;
    }

}
