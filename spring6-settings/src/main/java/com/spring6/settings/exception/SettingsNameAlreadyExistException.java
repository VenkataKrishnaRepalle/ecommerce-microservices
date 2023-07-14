package com.spring6.settings.exception;


import lombok.Getter;

@Getter
public class SettingsNameAlreadyExistException extends RuntimeException {
    private final String errorCode;
    private final String dynamicValue;

    public SettingsNameAlreadyExistException(String errorCode, String dynamicValue) {
        super(String.format("%s : %s", errorCode, dynamicValue));

        this.errorCode = errorCode;
        this.dynamicValue = dynamicValue;
    }

}
