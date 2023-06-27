package com.spring6.brand.filter;

import com.spring6.common.utils.GlobalConstants;
import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class TraceIdFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String traceId = generateTraceId();
        setTraceIdInMdc(traceId);

        try {
            chain.doFilter(request, response);
        } finally {
            clearTraceIdFromMdc();
        }
    }

    private String generateTraceId() {
        return UUID.randomUUID().toString();
    }

    private void setTraceIdInMdc(String traceId) {
        MDC.put(GlobalConstants.TRACE_ID, traceId);
    }

    private void clearTraceIdFromMdc() {
        MDC.remove(GlobalConstants.TRACE_ID);
    }

}
