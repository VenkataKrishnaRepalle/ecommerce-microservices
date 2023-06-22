package com.spring6.brand.dto;

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
    @NotBlank(message = "E0001")
    @Size(min = 2, max = 45, message = "E0002")
    private String name;

    @NotNull(message = "E0003")
    private UUID subcategoryId;

    @NotNull(message = "E0004")
    @Enumerated
    private BrandStatusEnum status;
}
