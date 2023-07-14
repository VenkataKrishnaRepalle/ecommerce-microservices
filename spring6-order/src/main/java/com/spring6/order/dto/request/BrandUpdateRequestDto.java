package com.spring6.order.dto.request;

import com.spring6.common.enums.BrandStatusEnum;
import com.spring6.common.exeption.ErrorCodes;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BrandUpdateRequestDto {
    @NotBlank(message = ErrorCodes.E0005)
    @Size(min = 2, max = 45, message = ErrorCodes.E0006)
    private String name;

    @NotNull(message = ErrorCodes.E0007)
    @NotEmpty(message = ErrorCodes.E0008)
    private UUID subcategoryId;

    @NotNull(message = ErrorCodes.E0009)
    @Enumerated
    private BrandStatusEnum status;

}
