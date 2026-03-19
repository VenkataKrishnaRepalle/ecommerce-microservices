package com.pm.spring.ema.exception;

import lombok.Getter;

@Getter
public class FoundException extends RuntimeException {
  private final String errorCode;

  public FoundException(String errorCode) {
    super(String.format("%s", errorCode));

    this.errorCode = errorCode;
  }
}
