package com.pm.service.dto.request;

import com.pm.service.model.enums.RoleType;
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
