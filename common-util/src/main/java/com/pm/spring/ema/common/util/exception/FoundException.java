package com.pm.spring.ema.common.util.exception;

import lombok.Getter;

@Getter
public class FoundException extends RuntimeException {
    private final String errorCode;
    private final String errorMessage;

    public FoundException(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}