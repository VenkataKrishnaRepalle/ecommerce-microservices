package com.pm.spring.ema.common.util.dto;

import com.pm.spring.ema.common.util.enums.SubCategoryEnum;
import com.pm.spring.ema.common.util.exception.utils.ErrorCodes;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@Builder
public class SubCategoryDto {

    private UUID id;

    private String name;

    private String alias;

    private String imageName;

    private SubCategoryEnum status;

    private UUID categoryUUID;
}
