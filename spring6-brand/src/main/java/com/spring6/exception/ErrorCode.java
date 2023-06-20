package com.spring6.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    FIELD_REQUIRED("E0001", "Field is required"),
    INVALID_VALUE("E0002", "Invalid value");

    private final String code;
    private final String message;
}