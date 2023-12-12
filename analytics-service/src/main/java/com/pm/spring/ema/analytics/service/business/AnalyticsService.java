package com.pm.spring.ema.analytics.service.business;

import com.pm.spring.ema.analytics.service.model.MessageAnalyticsResponseModel;

import java.util.Optional;

public interface AnalyticsService {

    Optional<MessageAnalyticsResponseModel> getWordAnalytics(String word);
}

