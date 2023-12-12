package com.pm.spring.ema.user.dto.request;

import com.pm.spring.ema.user.model.enums.RoleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleCreateRequestDto {

    @NotNull
    private RoleType name;

    @NotBlank
    private String description;

}
