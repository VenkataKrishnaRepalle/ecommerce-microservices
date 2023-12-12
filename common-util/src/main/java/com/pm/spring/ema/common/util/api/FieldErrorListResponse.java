package com.pm.spring.ema.common.util.api;

import com.pm.spring.ema.common.util.api.StatusType;
import com.pm.spring.ema.common.util.exception.ErrorField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class FieldErrorListResponse {
    private StatusType status = StatusType.ERROR;
    private List<ErrorField> errors;

}