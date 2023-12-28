package com.pm.spring.ema.client.service.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClientAuthenticationMethodEnum {
    CLIENT_SECRET_BASIC("client_secret_basic"),
    CLIENT_SECRET_POST("client_secret_post"),
    CLIENT_SECRET_JWT("client_secret_jwt"),
   PRIVATE_KEY_JWT("private_key_jwt"),
    NONE("none");

    private final String value;

}
