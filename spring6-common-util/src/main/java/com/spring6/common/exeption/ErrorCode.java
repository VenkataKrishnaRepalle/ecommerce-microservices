package com.spring6.common.exeption;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    //    brand - 1 to 1000 errors
    E0001("E0001", "Field {0} is required."),
    E0002("E0002", "Field {0} should be 2 to 45 characters in length"),
    E0003("E0003", "Field {0} is required."),
    E0004("E0004", "Field {0} is required."),
    E0501("E0501", "Could not find any brand with ID : {0}"),
    E0502("E0502", "Could not find any brand with ID : {0}"),
    E0503("E0503", "Could not find any brand with ID : {0}"),
    E0504("E0504", "Could not find any brand with ID : {0}"),
    E0505("E0505", "Could not find any brand with ID : {0}"),
    E0506("E0506", "Brand name : {0} already exist"),
    E0500("E0500", "Internal Server Error - {0}");

    private final String code;
    private final String message;
}