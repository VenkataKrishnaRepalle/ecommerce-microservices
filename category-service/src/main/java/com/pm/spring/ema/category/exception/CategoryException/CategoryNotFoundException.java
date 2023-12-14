package com.pm.spring.ema.category.exception.CategoryException;


import lombok.Getter;

@Getter
public class CategoryNotFoundException extends RuntimeException {
    private final String errorCode;
    private final String dynamicValue;

    public CategoryNotFoundException(String errorCode, String dynamicValue) {
        super(String.format("%s : %s", errorCode, dynamicValue));

        this.errorCode = errorCode;
        this.dynamicValue = dynamicValue;
    }
}
