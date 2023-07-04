package com.spring6.common.exeption;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorField {
    private String code;
    private String message;
    private String fieldName;
}
