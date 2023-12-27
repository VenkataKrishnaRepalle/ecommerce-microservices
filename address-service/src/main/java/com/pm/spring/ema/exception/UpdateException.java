package com.pm.spring.ema.exception;

import lombok.Getter;

@Getter
public class UpdateException extends RuntimeException{
    private final String errorCode;

    public UpdateException(String errorCode) {
        super(String.format("%s", errorCode));

        this.errorCode = errorCode;
    }

}
