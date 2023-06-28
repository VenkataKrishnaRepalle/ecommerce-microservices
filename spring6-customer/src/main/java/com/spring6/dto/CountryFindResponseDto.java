package com.spring6.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountryFindResponseDto {

    @NotNull
    private int code;

    @NotNull
    private String name;
}
