package com.pm.spring.ema.gateway.service.config;

import com.pm.spring.ema.app.config.GatewayServiceConfigData;
import com.pm.spring.ema.gateway.service.exception.AuthenticationHeaderNotFoundException;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Configuration
public class GatewayConfig {

    private final GatewayServiceConfigData gatewayServiceConfigData;

    private static final String HEADER_FOR_KEY_RESOLVER = "Authorization";

    public GatewayConfig(GatewayServiceConfigData configData) {
        this.gatewayServiceConfigData = configData;
    }

//    @Bean(name = "authHeaderResolver")
//    KeyResolver userKeyResolver() {
//        return exchange -> Mono.just(Objects.requireNonNull(exchange
//                .getRequest().getHeaders().getFirst(HEADER_FOR_KEY_RESOLVER)));
//    }

//    @Bean(name = "authHeaderResolver")
//    KeyResolver userKeyResolver() {
//
//        return exchange -> {
//            String headerValue = exchange.getRequest().getHeaders().getFirst(HEADER_FOR_KEY_RESOLVER);
//
//            if (headerValue != null) {
//                return Mono.just(headerValue);
//            } else {
//               log.error("Warning: The header " + HEADER_FOR_KEY_RESOLVER + " is null.");
//                throw new AuthenticationHeaderNotFoundException("The authentication header not found");
//            }
//        };
//    }

    @Bean
    Customizer<ReactiveResilience4JCircuitBreakerFactory> circuitBreakerFactoryCustomizer() {
        return reactiveResilience4JCircuitBreakerFactory ->
                reactiveResilience4JCircuitBreakerFactory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                        .timeLimiterConfig(TimeLimiterConfig.custom()
                                .timeoutDuration(Duration.ofMillis(gatewayServiceConfigData.getTimeoutMs()))
                                .build())
                        .circuitBreakerConfig(CircuitBreakerConfig.custom()
                                .failureRateThreshold(gatewayServiceConfigData.getFailureRateThreshold())
                                .slowCallRateThreshold(gatewayServiceConfigData.getSlowCallRateThreshold())
                                .slowCallDurationThreshold(Duration.ofMillis(gatewayServiceConfigData
                                        .getSlowCallDurationThreshold()))
                                .permittedNumberOfCallsInHalfOpenState(gatewayServiceConfigData
                                        .getPermittedNumOfCallsInHalfOpenState())
                                .slidingWindowSize(gatewayServiceConfigData.getSlidingWindowSize())
                                .minimumNumberOfCalls(gatewayServiceConfigData.getMinNumberOfCalls())
                                .waitDurationInOpenState(Duration.ofMillis(gatewayServiceConfigData
                                        .getWaitDurationInOpenState()))
                                .build())
                        .build());
    }

}
