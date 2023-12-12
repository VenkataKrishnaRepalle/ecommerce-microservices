package com.pm.spring.ema.mailservice.exception;

import lombok.Getter;

@Getter
public class InvalidInputException extends RuntimeException {
    private final String errorCode;

    public InvalidInputException(String errorCode) {
        super(String.format("%s", errorCode));

        this.errorCode = errorCode;
    }

}