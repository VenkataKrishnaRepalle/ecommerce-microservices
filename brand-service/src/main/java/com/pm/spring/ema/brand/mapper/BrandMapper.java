package com.pm.spring.ema.brand.mapper;

import com.pm.spring.ema.common.util.dto.BrandDto;
import com.pm.spring.ema.brand.entity.Brand;
import org.mapstruct.Mapper;

@Mapper
public interface BrandMapper {

  Brand toBrand(BrandDto brandDto);

  BrandDto toBrandDto(Brand brand);
}
