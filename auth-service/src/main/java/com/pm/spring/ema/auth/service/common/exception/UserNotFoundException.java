package com.pm.spring.ema.auth.service.common.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
    private final String error;

    public UserNotFoundException(String error) {
        super(String.format("%s", error));

        this.error = error;
    }

}
