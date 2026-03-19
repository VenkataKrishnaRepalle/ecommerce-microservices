package com.pm.spring.ema.common.util.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class SuccessResponse {
  private StatusType status = StatusType.SUCCESS;
  private Object data;
}
