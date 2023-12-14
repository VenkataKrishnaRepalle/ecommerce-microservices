package com.spring6.ecommerce.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    E1001("E0001", "Field {0} is required."),
    E1002("E0002", "Invalid value for field : {0}"),
    E1501("E1501", "Could not find any product with ID : {0}"),
    E1502("E1502", "Product already present with name : {0}"),
    E1503("E1503", "Product already present with name : {0}");

    private final String code;
    private final String message;
}
