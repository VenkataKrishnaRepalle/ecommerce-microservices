package com.spring6.common.utils;

import org.slf4j.MDC;

public class TraceIdHolder {
    public static String traceId = MDC.get(GlobalConstants.TRACE_ID);
}
