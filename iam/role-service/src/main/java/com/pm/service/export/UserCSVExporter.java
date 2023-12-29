package com.pm.service.export;

import com.pm.service.dto.response.UserFindResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.util.List;

@Component
public class UserCSVExporter extends AbstractExporter {
    public void export(List<UserFindResponseDto> userList, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "text/csv", ".csv");

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"Id", "Email", "First Name", "Last Name", "Username", "Status"};
        String[] fieldMapping = {"id", "email", "firstName", "lastName", "username", "status"};

        csvWriter.writeHeader(csvHeader);

        for (UserFindResponseDto userFindResponseDto : userList) {
            csvWriter.write(userFindResponseDto, fieldMapping);
        }

        csvWriter.close();

    }
}
