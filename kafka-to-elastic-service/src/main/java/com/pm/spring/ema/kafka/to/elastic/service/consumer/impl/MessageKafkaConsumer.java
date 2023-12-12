package com.pm.spring.ema.kafka.to.elastic.service.consumer.impl;

import com.pm.spring.ema.kafka.to.elastic.service.consumer.KafkaConsumer;
import com.pm.spring.ema.kafka.to.elastic.service.mapper.MessageMapper;
import com.pm.spirng.chat.elastic.model.index.impl.MessageElasticIndexModel;
import com.pm.spring.ema.app.config.KafkaConfigData;
import com.pm.spring.ema.app.config.KafkaConsumerConfigData;
import com.pm.spring.ema.elastic.index.client.service.ElasticIndexClient;
import com.pm.spring.ema.kafka.admin.client.KafkaAdminClient;
import com.pm.spring.ema.kafka.model.MessageKafkaModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageKafkaConsumer implements KafkaConsumer<MessageKafkaModel> {

    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    private final KafkaAdminClient kafkaAdminClient;

    private final KafkaConfigData kafkaConfigData;

    private final KafkaConsumerConfigData kafkaConsumerConfigData;

    private final MessageMapper messageMapper;

    private final ElasticIndexClient<MessageElasticIndexModel> elasticIndexClient;

    @EventListener
    public void onAppStarted(ApplicationStartedEvent event) {
        kafkaAdminClient.checkTopicsCreated();
        log.info("Topics with name {} is ready for operations!", kafkaConfigData.getTopicNamesToCreate().toArray());
        Objects.requireNonNull(kafkaListenerEndpointRegistry
                .getListenerContainer(kafkaConsumerConfigData.getConsumerGroupId())).start();
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.consumer-group-id}", topics = "${kafka-config.topic-name}")
    public void receive(@Payload List<MessageKafkaModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<Long> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number of message received with keys {}, partitions {} and offsets {}, " +
                        "sending it to elastic: Thread id {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString(),
                Thread.currentThread().getId());

        List<MessageElasticIndexModel> messageElasticIndexModels = messages.stream()
                .map(messageMapper::convertToMessageElasticIndexModel)
                .collect(Collectors.toList());

        List<String> documentIds = elasticIndexClient.save(messageElasticIndexModels);
        log.info("Documents saved to elasticsearch with ids {}", documentIds.toArray());
    }
}
