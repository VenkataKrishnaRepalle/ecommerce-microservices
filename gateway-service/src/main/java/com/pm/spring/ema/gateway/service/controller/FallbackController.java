package com.pm.spring.ema.gateway.service.controller;

import com.pm.spring.ema.gateway.service.model.AnalyticsDataFallbackModel;
import com.pm.spring.ema.gateway.service.model.AuthenticationServiceFallbackModel;
import com.pm.spring.ema.gateway.service.model.MessageServiceFallbackModel;
import com.pm.spring.ema.gateway.service.model.QueryServiceFallbackModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    private static final Logger LOG = LoggerFactory.getLogger(FallbackController.class);

//    @Value("${server.port}")
//    private String port;
//
//    @PostMapping("/query-fallback")
//    public ResponseEntity<QueryServiceFallbackModel> queryServiceFallback() {
//        LOG.info("Returning fallback result for elastic-query-service! on port {}", port);
//        return ResponseEntity.ok(QueryServiceFallbackModel.builder()
//                .fallbackMessage("Fallback result for elastic-query-service!")
//                .build());
//    }
//
//    @PostMapping("/analytics-fallback")
//    public ResponseEntity<AnalyticsDataFallbackModel> analyticsServiceFallback() {
//        LOG.info("Returning fallback result for analytics-service! on port {}", port);
//        return ResponseEntity.ok(AnalyticsDataFallbackModel.builder()
//                .wordCount(0L)
//                .build());
//    }
//
//
//    @PostMapping("/streams-fallback")
//    public ResponseEntity<AnalyticsDataFallbackModel> streamsServiceFallback() {
//        LOG.info("Returning fallback result for kafka-streams-service! on port {}", port);
//        return ResponseEntity.ok(AnalyticsDataFallbackModel.builder()
//                .wordCount(0L)
//                .build());
//    }
//
//    @PostMapping("/authentication-fallback")
//    public ResponseEntity<AuthenticationServiceFallbackModel> authenticationServiceFallback() {
//        LOG.info("Returning fallback result for authentication! on port {}", port);
//        return ResponseEntity.ok(AuthenticationServiceFallbackModel.builder()
//                .fallbackMessage("Fallback result for authentication-service!")
//                .build());
//    }
//    @PostMapping("/message-fallback")
//    public ResponseEntity<MessageServiceFallbackModel> messageServiceFallback() {
//        LOG.info("Returning fallback result for message! on port {}", port);
//        return ResponseEntity.ok(MessageServiceFallbackModel.builder()
//                .fallbackMessage("Fallback result for message-service!")
//                .build());
//    }



}
