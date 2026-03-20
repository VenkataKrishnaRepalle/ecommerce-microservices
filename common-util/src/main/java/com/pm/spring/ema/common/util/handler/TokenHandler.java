package com.pm.spring.ema.common.util.handler;

import com.pm.spring.ema.common.util.dto.KeyCloakTokenResponse;
import com.pm.spring.ema.common.util.dto.TokenDto;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
public class TokenHandler {

  private final WebClient webClient;
  private final String clientId;
  private final String clientSecret;

  private volatile TokenDto token;

  public TokenHandler(
      WebClient.Builder webClientBuilder,
      @Value("${keycloak.token-base-url}") String tokenBaseUrl,
      @Value("${keycloak.client-id}") String clientId,
      @Value("${keycloak.client-secret}") String clientSecret) {
    this.webClient = webClientBuilder.baseUrl(tokenBaseUrl).build();
    this.clientId = clientId;
    this.clientSecret = clientSecret;
  }

  public synchronized String getToken() {
    if (token != null && !token.isExpired()) {
      return token(token.getToken());
    }

    KeyCloakTokenResponse tokenResponse = getKeycloakToken();
    log.info("New Token generated: {}", tokenResponse.toString());
    token =
        TokenDto.builder()
            .token(tokenResponse.getAccessToken())
            .expiryTime(Instant.now().plusSeconds(tokenResponse.getExpiresIn() - 30))
            .build();

    return token(token.getToken());
  }

  private String token(String token) {
    return "Bearer " + token;
  }

  private KeyCloakTokenResponse getKeycloakToken() {
    MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
    request.add("grant_type", "client_credentials");
    request.add("client_id", clientId);
    request.add("client_secret", clientSecret);

    return webClient
        .post()
        .uri("/token")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .bodyValue(request)
        .retrieve()
        .bodyToMono(KeyCloakTokenResponse.class)
        .block();
  }
}
