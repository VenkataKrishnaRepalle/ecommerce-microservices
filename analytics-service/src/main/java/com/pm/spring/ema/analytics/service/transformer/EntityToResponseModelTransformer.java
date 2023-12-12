package com.pm.spring.ema.analytics.service.transformer;

import com.pm.spring.ema.analytics.service.dataaccess.entity.MessageAnalyticsEntity;
import com.pm.spring.ema.analytics.service.model.MessageAnalyticsResponseModel;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EntityToResponseModelTransformer {

    public Optional<MessageAnalyticsResponseModel> getResponseModel(MessageAnalyticsEntity twitterMessageAnalyticsEntity) {
        if (twitterMessageAnalyticsEntity == null)
            return Optional.empty();
        return Optional.ofNullable(MessageAnalyticsResponseModel
                .builder()
                .id(twitterMessageAnalyticsEntity.getId())
                .word(twitterMessageAnalyticsEntity.getWord())
                .wordCount(twitterMessageAnalyticsEntity.getWordCount())
                .build());
    }
}
