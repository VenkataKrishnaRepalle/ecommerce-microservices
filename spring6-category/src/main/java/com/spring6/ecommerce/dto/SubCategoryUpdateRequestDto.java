package com.spring6.ecommerce.dto;

import com.spring6.common.enums.SubCategoryEnum;
import com.spring6.common.exeption.ErrorCodes;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubCategoryUpdateRequestDto {
    @NotBlank(message = ErrorCodes.E1017)
    @Size(min = 2, max = 45, message = ErrorCodes.E1018)
    private String name;
    @NotBlank(message = ErrorCodes.E1019)
    private String alias;
    @NotBlank(message = ErrorCodes.E1020)
    private String image;
    @NotNull(message = ErrorCodes.E1021)
    @Enumerated
    private SubCategoryEnum status;

}
