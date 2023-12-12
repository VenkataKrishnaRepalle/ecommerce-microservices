package com.pm.spring.ema.kafka.to.elastic.service.consumer;

import java.util.List;

public interface KafkaConsumer<T> {
    void receive(List<T> messages, List<Long> keys, List<Integer> partitions, List<Long> offsets);
}
