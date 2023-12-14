package com.pm.spring.ema.auth.exception;

import lombok.Getter;

@Getter
public class InvalidTokenException extends RuntimeException {
    private final String error;

    public InvalidTokenException(String error) {
        super(error);

        this.error = error;
    }

}
