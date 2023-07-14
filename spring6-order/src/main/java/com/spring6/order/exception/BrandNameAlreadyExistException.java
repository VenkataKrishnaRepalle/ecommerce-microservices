package com.spring6.order.exception;

import lombok.Getter;

@Getter
public class BrandNameAlreadyExistException extends RuntimeException {
    private final String errorCode;
    private final String dynamicValue;

    public BrandNameAlreadyExistException(String errorCode, String dynamicValue) {
        super(String.format("%s : %s", errorCode, dynamicValue));

        this.errorCode = errorCode;
        this.dynamicValue = dynamicValue;
    }
}
