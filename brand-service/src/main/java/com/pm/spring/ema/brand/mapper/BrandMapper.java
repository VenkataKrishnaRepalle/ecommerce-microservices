package com.pm.spring.ema.brand.mapper;

import com.pm.spring.ema.brand.dto.BrandAuditDto;
import com.pm.spring.ema.brand.dto.BrandDto;
import com.pm.spring.ema.brand.model.entity.Brand;
import com.pm.spring.ema.common.util.dto.BrandFindResponseDto;
import org.mapstruct.Mapper;

@Mapper
public interface BrandMapper {

  BrandFindResponseDto toBrandFindResponseDto(Brand brand);

  Brand toBrand(BrandDto brandDto);

  BrandDto toBrandDto(Brand brand);

  BrandAuditDto brandToBrandAuditResponseDto(Brand brand);
}
