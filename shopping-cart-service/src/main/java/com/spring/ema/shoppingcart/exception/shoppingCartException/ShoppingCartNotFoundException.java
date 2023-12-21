package com.spring.ema.shoppingcart.exception.shoppingCartException;


import lombok.Getter;

@Getter
public class ShoppingCartNotFoundException extends RuntimeException {
    private final String errorCode;
    private final String dynamicValue;

    public ShoppingCartNotFoundException(String errorCode, String dynamicValue) {
        super(String.format("%s : %s", errorCode, dynamicValue));

        this.errorCode = errorCode;
        this.dynamicValue = dynamicValue;
    }
}
