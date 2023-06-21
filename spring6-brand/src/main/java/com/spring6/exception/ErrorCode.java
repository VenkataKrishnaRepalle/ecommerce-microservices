package com.spring6.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    FORM_VALIDATION("E5000", "Validation Failed"),
    INVALID_VALUE("E5000", "Invalid value");

    private final String code;
    private final String message;
}