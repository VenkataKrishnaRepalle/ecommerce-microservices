package com.pm.spring.ema.order.dto.response;


import com.pm.spring.ema.common.enums.BrandStatusEnum;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class OrderCreateResponseDto {
    private UUID id;
    private String name;
    private UUID subcategoryId;
    private BrandStatusEnum status;
}
