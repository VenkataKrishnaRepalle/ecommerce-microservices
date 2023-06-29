package com.spring6.user.utils;

import com.spring6.common.utils.GlobalConstants;
import com.spring6.user.dto.UserFindResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class UserCSVExporter {
    public void export(List<UserFindResponseDto> userFindResponseDtoList, HttpServletResponse response) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timestamp = dateFormat.format(new Date());

        String fileName = "users_" + timestamp + ".csv";

        response.setContentType("text/csv");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;

        response.setHeader(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());
        response.setHeader(headerKey, headerValue);

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"User Id", "Email", "First Name", "Last Name", "Username", "Status"};
        String[] fieldMapping = {"id", "email", "firstName", "lastName", "username", "status"};

        csvWriter.writeHeader(csvHeader);

        for(UserFindResponseDto userFindResponseDto : userFindResponseDtoList) {
            csvWriter.write(userFindResponseDto, fieldMapping);
        }

        csvWriter.close();

    }
}
