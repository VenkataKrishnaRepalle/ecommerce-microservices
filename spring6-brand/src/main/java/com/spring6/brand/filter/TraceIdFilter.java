package com.spring6.brand.filter;

import com.spring6.brand.utils.TraceIdHolder;
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

        TraceIdHolder.generateTraceId();
        try {
            chain.doFilter(request, response);
        } finally {
            TraceIdHolder.clearTraceId();
        }
    }

}
