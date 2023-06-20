package com.spring6.dto;

import com.spring6.common.enums.BrandStatusEnum;
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
    private UUID id;
    @NotBlank
    @Size(min = 2, max = 45)
    private String name;

    @NotBlank
    private String logo;

    @NotNull
    @NotEmpty
    private UUID subcategoryId;

    @NotNull
    private BrandStatusEnum status;


}
