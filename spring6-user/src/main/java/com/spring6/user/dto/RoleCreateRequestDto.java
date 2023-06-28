package com.spring6.user.dto;

import com.spring6.user.entity.RoleType;
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
