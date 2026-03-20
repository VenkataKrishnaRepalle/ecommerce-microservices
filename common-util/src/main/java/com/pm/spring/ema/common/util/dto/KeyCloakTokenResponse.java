package com.pm.spring.ema.common.util.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class KeyCloakTokenResponse {

  @JsonProperty("access_token")
  private String accessToken;

  @JsonProperty("expires_in")
  private int expiresIn;

  @JsonProperty("refresh_expires_in")
  private int refreshExpiresIn;

  @JsonProperty("token_type")
  private String tokenType;

  @JsonProperty("not-before-policy")
  private int notBeforePolicy;

  private String scope;
}
