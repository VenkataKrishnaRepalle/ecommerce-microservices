package com.spring6.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.management.loading.MLetContent;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    E0001("E0001", "Field {0} is required."),
    E0002("E0002", "Invalid value for field : {0}"),
    E0501("E0501", "Could not find any brand with ID : {0}"),
    E0502("E0502", "Could not find any brand with ID : {0}");

    private final String code;
    private final String message;
}