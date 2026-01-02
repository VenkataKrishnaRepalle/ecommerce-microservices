package com.pm.spring.ema.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountryDto {

    @NotNull
    private String code;

    @NotNull
    private String name;
}
