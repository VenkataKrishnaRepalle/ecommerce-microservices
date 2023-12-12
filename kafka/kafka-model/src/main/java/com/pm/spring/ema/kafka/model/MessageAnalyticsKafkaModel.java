package com.pm.spring.ema.kafka.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class MessageAnalyticsKafkaModel {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("senderId")
    private UUID senderId;

    @JsonProperty("receiverId")
    private UUID receiverId;

    @JsonProperty("message")
    private String message;

    @JsonProperty("status")
    private String status;

    @JsonProperty("createdAt")
    private Date createdAt;

    @JsonProperty("updatedAt")
    private Date updatedAt;
}
