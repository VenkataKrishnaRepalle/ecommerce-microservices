package com.pm.spring.ema.authentication.common.filter;

import com.pm.spring.ema.authentication.common.util.TraceIdHolder;
import jakarta.servlet.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
