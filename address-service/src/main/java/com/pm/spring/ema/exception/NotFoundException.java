package com.pm.spring.ema.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final String errorCode;

    public NotFoundException(String errorCode) {
        super(String.format("%s", errorCode));

        this.errorCode = errorCode;
    }
}
