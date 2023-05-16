package com.spring6.ecommerce.mapper;

import com.spring6.ecommerce.dto.BrandDto;
import com.spring6.ecommerce.entity.Brand;

public interface BrandMapper {
    Brand brandDtoToBrand(BrandDto brandDto);
    BrandDto brandToBrandDto(Brand brand);
}
