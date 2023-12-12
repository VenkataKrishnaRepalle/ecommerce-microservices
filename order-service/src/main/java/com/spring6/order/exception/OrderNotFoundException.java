package com.pm.spring.ema.order.exception;

import lombok.Getter;

@Getter
public class OrderNotFoundException extends RuntimeException {
    private final String errorCode;
    private final String dynamicValue;

    public OrderNotFoundException(String errorCode, String dynamicValue) {
        super(String.format("%s : %s", errorCode, dynamicValue));

        this.errorCode = errorCode;
        this.dynamicValue = dynamicValue;
    }

}
