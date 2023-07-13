package com.spring6.auth.util;

import com.spring6.common.utils.GlobalConstants;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.UUID;

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
