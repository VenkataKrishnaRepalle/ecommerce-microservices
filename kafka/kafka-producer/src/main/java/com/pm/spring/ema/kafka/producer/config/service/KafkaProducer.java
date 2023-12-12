package com.pm.spring.ema.kafka.producer.config.service;


public interface KafkaProducer<K, V> {
    void send(String topicName, K key, V message);
}
