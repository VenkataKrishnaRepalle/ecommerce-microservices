package com.pm.spring.ema.ecommerce.exception;


import lombok.Getter;

@Getter
public class SubCategoryNameAlreadyExistException extends RuntimeException {
    private final String errorCode;
    private final String dynamicValue;

    public SubCategoryNameAlreadyExistException(String errorCode, String dynamicValue) {
        super(String.format("%s : %s", errorCode, dynamicValue));

        this.errorCode = errorCode;
        this.dynamicValue = dynamicValue;
    }
}
