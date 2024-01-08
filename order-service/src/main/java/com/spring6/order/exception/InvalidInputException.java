package com.spring6.order.exception;

import lombok.Getter;

@Getter
public class InvalidInputException extends RuntimeException {
    private final String errorCode;

    public InvalidInputException(String errorCode) {
        super(String.format("%s : %s", errorCode));

        this.errorCode = errorCode;
    }
}
