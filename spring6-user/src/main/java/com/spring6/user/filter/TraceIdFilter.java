package com.spring6.user.filter;

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
