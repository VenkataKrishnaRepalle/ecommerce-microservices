package com.pm.spring.ema.client.service.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OidcScopesEnum {
    OPENID("openid"),
    PROFILE( "profile"),
    EMAIL( "email"),
    ADDRESS( "address"),
    PHONE( "phone");

    private final String value;

}