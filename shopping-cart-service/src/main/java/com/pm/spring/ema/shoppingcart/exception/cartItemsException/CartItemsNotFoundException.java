package com.pm.spring.ema.shoppingcart.exception.cartItemsException;


import lombok.Getter;

@Getter
public class CartItemsNotFoundException extends RuntimeException {
    private final String errorCode;
    private final String dynamicValue;

    public CartItemsNotFoundException(String errorCode, String dynamicValue) {
        super(String.format("%s : %s", errorCode, dynamicValue));

        this.errorCode = errorCode;
        this.dynamicValue = dynamicValue;
    }
}
