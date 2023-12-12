package com.pm.spring.ema.order.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Component
public class InstantFormatter {
    @Value("${rest.api.response.time-zone}")
    private static String restResponseTimezone;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter
            .ofLocalizedDateTime(FormatStyle.MEDIUM)
            .withZone(ZoneId.systemDefault());

    public static String formatInstant(Instant instant) {
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of(restResponseTimezone));
        return FORMATTER.format(zonedDateTime);
    }
}
