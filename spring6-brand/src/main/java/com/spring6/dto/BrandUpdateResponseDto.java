package com.spring6.dto;


import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BrandUpdateResponseDto {
    private UUID id;

    private String name;

    private String logo;

    private UUID subcategoryId;
}
