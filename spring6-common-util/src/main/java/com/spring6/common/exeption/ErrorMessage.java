package com.spring6.common.exeption;

import java.text.MessageFormat;

public class ErrorMessage {
    public static String message(String error, String dynamicValue) {
        return MessageFormat.format(error, dynamicValue);
    }

    public static ErrorResponse errorResponse(String error, String dynamicValue) {
        String[] errorCodeAndMessage = error.split("-");
        String errorCode = errorCodeAndMessage[0];
        String errorMessage = errorCodeAndMessage[1];

        return ErrorResponse.builder()
                .error(Error.builder()
                        .code(errorCode)
                        .message(MessageFormat.format(errorMessage, dynamicValue))
                        .build())
                .build();
    }

    public static ErrorResponse errorResponse(String error) {
        String[] errorCodeAndMessage = error.split("-");
        String errorCode = errorCodeAndMessage[0];
        String errorMessage = errorCodeAndMessage[1];

        return ErrorResponse.builder()
                .error(Error.builder()
                        .code(errorCode)
                        .message(errorMessage)
                        .build())
                .build();
    }
}
