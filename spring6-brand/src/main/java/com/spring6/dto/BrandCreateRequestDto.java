package com.spring6.dto;

import com.spring6.common.enums.BrandStatusEnum;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BrandCreateRequestDto {
    @NotBlank(message = "{E1000}")
    @Size(min = 2, max = 45, message = "{E1001}")
    private String name;

    @NotNull(message = "{E1002}")
    private UUID subcategoryId;

    @NotNull
    @Enumerated
    private BrandStatusEnum status;
}
