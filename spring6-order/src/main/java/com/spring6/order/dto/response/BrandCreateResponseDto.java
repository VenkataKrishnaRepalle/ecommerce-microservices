package com.spring6.order.dto.response;


import com.spring6.common.enums.BrandStatusEnum;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BrandCreateResponseDto {
    private UUID id;
    private String name;
    private UUID subcategoryId;
    private BrandStatusEnum status;
}
