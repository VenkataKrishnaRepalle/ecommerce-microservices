package com.spring6.order.exception;

import lombok.Getter;

@Getter
public class OrderQuantityException extends RuntimeException {
    private final String errorCode;

    public OrderQuantityException(String errorCode) {
        super(String.format("%s", errorCode));

        this.errorCode = errorCode;
    }
}
