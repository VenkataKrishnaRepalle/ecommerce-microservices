package com.pm.spring.ema.common.util.exception;

import lombok.Getter;

@Getter
public class InvalidInputException extends RuntimeException {
  private final String errorCode;
  private final String errorMessage;

  public InvalidInputException(String errorCode, String errorMessage) {
    super(String.format("%s, %s", errorCode, errorMessage));

    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }
}
