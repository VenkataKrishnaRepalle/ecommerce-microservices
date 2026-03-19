package com.pm.spring.ema.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Limit reached!")
public class LimitException extends RuntimeException {
  private final String errorCode;

  public LimitException(String errorCode) {
    super(String.format("%s", errorCode));
    this.errorCode = errorCode;
  }
}
