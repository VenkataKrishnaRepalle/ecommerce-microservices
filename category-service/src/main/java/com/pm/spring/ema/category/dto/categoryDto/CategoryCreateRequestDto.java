package com.pm.spring.ema.category.dto.categoryDto;

import com.pm.spring.ema.common.enums.CategoryEnum;
import com.pm.spring.ema.common.util.exception.ErrorCodes;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryCreateRequestDto {
    @NotBlank(message = ErrorCodes.E1001)
    @Size(min = 2, max = 45, message = ErrorCodes.E1002)
    private String name;

    @NotBlank(message = ErrorCodes.E1003)
    private String alias;

    @NotBlank(message = ErrorCodes.E1004)
    private String image;

    @NotNull(message = ErrorCodes.E1005)
    @Enumerated
    private CategoryEnum status;

}
