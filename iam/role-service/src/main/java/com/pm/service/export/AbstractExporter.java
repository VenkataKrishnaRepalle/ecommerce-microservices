package com.pm.service.export;

import com.pm.service.filter.TraceIdHolder;
import com.pm.spring.ema.common.util.GlobalConstants;
import jakarta.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AbstractExporter {
    public void setResponseHeader(HttpServletResponse response, String contentType, String extension) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String fileName = "users_" + timestamp + extension;

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());
        response.setHeader(headerKey, headerValue);
        response.setContentType(contentType);
    }
}
