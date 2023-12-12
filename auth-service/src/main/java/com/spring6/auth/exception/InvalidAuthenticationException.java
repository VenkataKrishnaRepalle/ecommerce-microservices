package com.pm.spring.ema.auth.exception;

import lombok.Getter;

@Getter
public class InvalidAuthenticationException extends RuntimeException {
    private final String error;

    public InvalidAuthenticationException(String error) {
        super(error);

        this.error = error;
    }

}
