package com.spring6.common.exeption;

import java.text.MessageFormat;

public class ErrorMessage {
    public static String message(String errorCode, String dynamicValue) {
        return MessageFormat.format(ErrorCode.valueOf(errorCode).getMessage(), dynamicValue);
    }

    public static ErrorResponse errorResponse(String errorCode, String dynamicValue) {
        return ErrorResponse.builder()
                .error(Error.builder()
                        .code(errorCode)
                        .message(MessageFormat.format(ErrorCode.valueOf(errorCode).getMessage(), dynamicValue))
                        .build())
                .build();
    }
}
