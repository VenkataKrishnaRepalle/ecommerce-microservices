package com.pm.spring.ema.dto.request;

import com.pm.spring.ema.dto.enums.GrantType;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ClientCreateRequestDto {
    private String name;
    private String clientSecret;
    private List<GrantType> grantTypes;
    private String scopes;
    private String redirectUris;

}
