package com.spring6.user.export;

import com.spring6.common.utils.GlobalConstants;
import com.spring6.user.utils.TraceIdHolder;
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
