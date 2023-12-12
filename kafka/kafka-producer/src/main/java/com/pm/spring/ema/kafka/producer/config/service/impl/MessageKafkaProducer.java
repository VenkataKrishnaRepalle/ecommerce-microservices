package com.pm.spring.ema.kafka.producer.config.service.impl;

import com.pm.spring.ema.kafka.producer.config.service.KafkaProducer;
import com.pm.spring.ema.kafka.model.MessageKafkaModel;
import jakarta.annotation.PreDestroy;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

@Service
public class MessageKafkaProducer implements KafkaProducer<UUID, MessageKafkaModel> {

    private static final Logger LOG = LoggerFactory.getLogger(MessageKafkaProducer.class);

    private KafkaTemplate<UUID, MessageKafkaModel> kafkaTemplate;

    public MessageKafkaProducer(KafkaTemplate<UUID, MessageKafkaModel> template) {
        this.kafkaTemplate = template;
    }

    @Override
    public void send(String topicName, UUID key, MessageKafkaModel messageKafkaModel) {
        LOG.info("Sending message='{}' to topic='{}'", messageKafkaModel, topicName);
        CompletableFuture<SendResult<UUID, MessageKafkaModel>> kafkaResultFuture = kafkaTemplate.send(topicName, key, messageKafkaModel);
        kafkaResultFuture.whenComplete(getCallback(topicName, messageKafkaModel));
    }

    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            LOG.info("Closing kafka producer!");
            kafkaTemplate.destroy();
        }
    }

    private BiConsumer<SendResult<UUID, MessageKafkaModel>, Throwable> getCallback(String topicName, MessageKafkaModel messageKafkaModel) {
        return (result, ex) -> {
            if (ex == null) {
                RecordMetadata metadata = result.getRecordMetadata();
                LOG.info("Received new metadata. Topic: {}; Partition {}; Offset {}; Timestamp {}, at time {}",
                        metadata.topic(),
                        metadata.partition(),
                        metadata.offset(),
                        metadata.timestamp(),
                        System.nanoTime());
            } else {
                LOG.error("Error while sending message {} to topic {}", messageKafkaModel.toString(), topicName, ex);
            }
        };
    }
}
