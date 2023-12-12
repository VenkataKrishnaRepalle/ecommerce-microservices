package com.pm.spring.ema.mailservice.exception;

import lombok.Getter;

@Getter
public class FeignClientException extends RuntimeException {
    private final String errorCode;
    private final String dynamicValue;

    public FeignClientException(String errorCode, String dynamicValue) {
        super(String.format("%s : %s", errorCode, dynamicValue));

        this.errorCode = errorCode;
        this.dynamicValue = dynamicValue;
    }

}
