package com.pm.spring.ema.common.util.exception.utils;

import com.pm.spring.ema.common.util.api.ErrorResponse;
import com.pm.spring.ema.common.util.api.StatusType;

import java.text.MessageFormat;

public class ErrorMessage {
    public static String message(String error, String dynamicValue) {
        return MessageFormat.format(error, dynamicValue);
    }

    public static ErrorResponse errorResponse(String error, String dynamicValue) {
        String[] errorCodeAndMessage = error.split("-", 1);
        String errorCode = errorCodeAndMessage[0];
        String errorMessage = errorCodeAndMessage[1];

        return ErrorResponse.builder()
                .status(StatusType.ERROR)
                .error(Error.builder()
                        .code(errorCode)
                        .message(MessageFormat.format(errorMessage, dynamicValue))
                        .build())
                .build();
    }

    public static ErrorResponse errorResponse(String error) {
        String[] errorCodeAndMessage = error.split("-", 2);
        String errorCode = errorCodeAndMessage[0];
        String errorMessage = errorCodeAndMessage[1];

        return ErrorResponse.builder()
                .status(StatusType.ERROR)
                .error(Error.builder()
                        .code(errorCode)
                        .message(errorMessage)
                        .build())
                .build();
    }
}
