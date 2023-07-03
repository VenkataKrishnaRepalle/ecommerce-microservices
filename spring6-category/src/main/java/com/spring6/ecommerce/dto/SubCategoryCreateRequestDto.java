package com.spring6.ecommerce.dto;

import com.spring6.common.enums.SubCategoryEnum;
import com.spring6.common.exeption.ErrorCodes;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@Builder
public class SubCategoryCreateRequestDto {
    @NotBlank(message = ErrorCodes.E1011)
    @Size(min = 2, max = 45, message = ErrorCodes.E1012)
    private String name;
    @NotBlank(message = ErrorCodes.E1013)
    private String alias;
    @NotBlank(message = ErrorCodes.E1014)
    private String image;
    @NotNull(message = ErrorCodes.E1015)
    private SubCategoryEnum status;
    @NotNull(message = ErrorCodes.E1016)
    @Enumerated
    private UUID categoryUUID;
}
