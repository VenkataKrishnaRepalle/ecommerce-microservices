package com.pm.spring.ema.brand.dto;

import com.pm.spring.ema.common.util.enums.BrandStatusEnum;
import com.pm.spring.ema.common.util.exception.utils.ErrorCodes;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BrandDto {

    private UUID id;

    @NotBlank(message = ErrorCodes.E0001)
    @Size(min = 2, max = 45, message = ErrorCodes.E0002)
    private String name;

    @NotNull(message = ErrorCodes.E0003)
    private UUID subcategoryId;

    @NotNull(message = ErrorCodes.E0004)
    @Enumerated
    private BrandStatusEnum status;
}
