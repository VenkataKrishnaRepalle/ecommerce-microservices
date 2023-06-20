package com.spring6.mapper;

import com.spring6.common.dto.BrandFindResponesDto;
import com.spring6.dto.BrandCreateRequestDto;
import com.spring6.dto.BrandCreateResponseDto;
import com.spring6.dto.BrandUpdateRequestDto;
import com.spring6.dto.BrandUpdateResponseDto;
import com.spring6.entity.Brand;
import org.mapstruct.Mapper;

@Mapper
public interface BrandMapper {

    BrandFindResponesDto brandToBrandFindResponesDto(Brand brand);

    Brand brandCreateRequestDtoToBrand(BrandCreateRequestDto brandCreateRequestDto);

    BrandCreateResponseDto brandToBrandCreateResponseDto(Brand brand);

    Brand brandUpdateRequestDtoToBrand(BrandUpdateRequestDto brandUpdateRequestDto);

    BrandUpdateResponseDto brandToBrandUpdateResponseDto(Brand brand);
}
