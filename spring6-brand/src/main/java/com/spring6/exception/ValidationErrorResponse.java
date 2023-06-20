package com.spring6.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ValidationErrorResponse {
    private String code;
    private String message;
    private List<ErrorResponse> errors;
}