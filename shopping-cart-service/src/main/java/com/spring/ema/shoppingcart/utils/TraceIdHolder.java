<<<<<<<< HEAD:auth-service/src/main/java/com/pm/spring/ema/auth/service/common/util/TraceIdHolder.java
package com.pm.spring.ema.auth.service.common.util;
========
package com.spring.ema.shoppingcart.utils;
>>>>>>>> main:shopping-cart-service/src/main/java/com/spring/ema/shoppingcart/utils/TraceIdHolder.java

import com.pm.spring.ema.common.util.GlobalConstants;
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
