package com.pm.spring.ema.group.service.dto.request;

import com.pm.spring.ema.group.service.dto.enums.GrantType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ClientCreateRequestDto {
    private String name;
    private String clientSecret;
    private List<GrantType> grantTypes;
    private String scopes;
    private String redirectUris;

}
