package com.spring6.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private String errorCode;
    private String errorMessage;
}
