package com.pm.spring.ema.common.util.exception.utils;

import com.pm.spring.ema.common.util.api.ErrorResponse;
import com.pm.spring.ema.common.util.api.StatusType;
import java.text.MessageFormat;

public class ErrorMessage {
  public static String message(String error, String dynamicValue) {
    return MessageFormat.format(error, dynamicValue);
  }

  public static ErrorResponse errorResponse(String error, String dynamicValue) {

    return ErrorResponse.builder().status(StatusType.ERROR).message(error + dynamicValue).build();
  }

  public static ErrorResponse errorResponse(String error) {

    return ErrorResponse.builder().status(StatusType.ERROR).message(error).build();
  }
}
