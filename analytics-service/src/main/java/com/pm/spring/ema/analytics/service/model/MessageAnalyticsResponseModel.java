package com.pm.spring.ema.analytics.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageAnalyticsResponseModel {
    private UUID id;
    private String word;
    private long wordCount;

}
