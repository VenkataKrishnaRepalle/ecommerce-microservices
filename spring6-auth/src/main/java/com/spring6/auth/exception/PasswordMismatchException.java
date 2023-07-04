package com.spring6.auth.exception;

import lombok.Getter;

@Getter
public class PasswordMismatchException extends RuntimeException {
    private final String error;

    public PasswordMismatchException(String error) {
        super(String.format("%s", error));

        this.error = error;
    }

}
