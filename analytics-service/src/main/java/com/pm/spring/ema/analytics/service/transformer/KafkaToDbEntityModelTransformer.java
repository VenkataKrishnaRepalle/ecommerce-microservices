package com.pm.spring.ema.analytics.service.transformer;

import com.pm.spring.ema.analytics.service.dataaccess.entity.MessageAnalyticsEntity;
import com.pm.spring.ema.kafka.model.MessageAnalyticsKafkaModel;
import org.springframework.stereotype.Component;
import org.springframework.util.IdGenerator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class KafkaToDbEntityModelTransformer {

    private final IdGenerator idGenerator;

    public KafkaToDbEntityModelTransformer(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public List<MessageAnalyticsEntity> getEntityModel(List<MessageAnalyticsKafkaModel> messageAnalyticsKafkaModelList) {
        return messageAnalyticsKafkaModelList.stream()
                .map(messageAnalyticsKafkaModel ->
                        MessageAnalyticsEntity.builder()
                                .id(idGenerator.generateId())
                                .word(messageAnalyticsKafkaModel.getMessage())
                                .wordCount(10L)
                                .build())
                .collect(toList());
    }


}
