package com.pm.spring.ema.brand.mapper;

import com.pm.spring.ema.brand.dto.request.BrandCreateRequestDto;
import com.pm.spring.ema.brand.dto.request.BrandUpdateRequestDto;
import com.pm.spring.ema.brand.dto.response.BrandAuditResponseDto;
import com.pm.spring.ema.brand.dto.response.BrandCreateResponseDto;
import com.pm.spring.ema.brand.dto.response.BrandUpdateResponseDto;
import com.pm.spring.ema.brand.model.entity.Brand;
import com.pm.spring.ema.common.util.dto.BrandFindResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.format.datetime.standard.InstantFormatter;

import java.time.Instant;

@Mapper
public interface BrandMapper {

    BrandFindResponseDto brandToBrandFindResponseDto(Brand brand);

    Brand brandCreateRequestDtoToBrand(BrandCreateRequestDto brandCreateRequestDto);

    BrandCreateResponseDto brandToBrandCreateResponseDto(Brand brand);

    Brand brandUpdateRequestDtoToBrand(BrandUpdateRequestDto brandUpdateRequestDto);

    BrandUpdateResponseDto brandToBrandUpdateResponseDto(Brand brand);

    BrandAuditResponseDto brandToBrandAuditResponseDto(Brand brand);

}
