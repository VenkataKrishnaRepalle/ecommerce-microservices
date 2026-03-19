package com.pm.spring.ema.category.filter;

import com.pm.spring.ema.category.utils.TraceIdHolder;
import jakarta.servlet.*;
import java.io.IOException;
import org.springframework.stereotype.Component;

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
