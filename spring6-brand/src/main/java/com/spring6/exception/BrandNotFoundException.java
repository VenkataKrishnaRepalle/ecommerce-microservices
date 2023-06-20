package com.spring6.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Value not found!")
public class BrandNotFoundException extends RuntimeException {
    private final String errorCode;
    private final String errorMessage;

    public BrandNotFoundException(String errorCode, String errorMessage) {
        super(String.format("%s : %s", errorCode, errorMessage));
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
