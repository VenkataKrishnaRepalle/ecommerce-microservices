package com.spring6.ecommerce.exception;


import lombok.Getter;

@Getter
public class CategoryNameAlreadyExistException extends RuntimeException {
    private final String errorCode;
    private final String dynamicValue;

    public CategoryNameAlreadyExistException(String errorCode, String dynamicValue) {
        super(String.format("%s : %s", errorCode, dynamicValue));

        this.errorCode = errorCode;
        this.dynamicValue = dynamicValue;
    }

}
