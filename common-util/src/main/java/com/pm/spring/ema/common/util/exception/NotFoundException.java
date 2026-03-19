package com.pm.spring.ema.common.util.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
  private final String errorCode;
  private final String errorMessage;

  public NotFoundException(String errorCode, String errorMessage) {
    super(String.format("%s", errorCode));
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }
}
