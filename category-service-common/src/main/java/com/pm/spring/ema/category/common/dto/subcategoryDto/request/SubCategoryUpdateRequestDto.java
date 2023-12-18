package com.pm.spring.ema.category.common.dto.subcategoryDto.request;

import com.pm.spring.ema.category.common.enums.SubCategoryEnum;
import com.pm.spring.ema.common.util.exception.ErrorCodes;
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
    private String imageName;

    @NotNull(message = ErrorCodes.E1021)
    @Enumerated
    private SubCategoryEnum status;

}
