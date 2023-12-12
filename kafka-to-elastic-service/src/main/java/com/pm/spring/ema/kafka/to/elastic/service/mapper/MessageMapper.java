package com.pm.spring.ema.kafka.to.elastic.service.mapper;

import com.pm.spirng.chat.elastic.model.index.impl.MessageElasticIndexModel;
import com.pm.spring.ema.kafka.model.MessageKafkaModel;
import org.mapstruct.Mapper;

@Mapper
public interface MessageMapper {
    MessageElasticIndexModel convertToMessageElasticIndexModel(MessageKafkaModel messageKafkaModel);
}