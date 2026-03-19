package com.pm.spring.ema.utils;

import com.pm.spring.ema.common.util.GlobalConstants;
import java.util.UUID;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class TraceIdHolder {

  public static void generateTraceId() {
    setTraceId(UUID.randomUUID().toString());
  }

  public static void setTraceId(String traceId) {
    MDC.put(GlobalConstants.TRACE_ID, traceId);
  }

  public static String getTraceId() {
    return MDC.get(GlobalConstants.TRACE_ID);
  }

  public static void clearTraceId() {
    MDC.remove(GlobalConstants.TRACE_ID);
  }
}
