package com.spring6.ecommerce.mapper;

import com.spring6.ecommerce.commondto.BrandFineResponesDto;
import com.spring6.ecommerce.dto.BrandCreateRequestDto;
import com.spring6.ecommerce.dto.BrandCreateResponseDto;
import com.spring6.ecommerce.dto.BrandUpdateRequestDto;
import com.spring6.ecommerce.dto.BrandUpdateResponseDto;
import com.spring6.ecommerce.entity.Brand;
import org.mapstruct.Mapper;

@Mapper
public interface BrandMapper {

    BrandFineResponesDto brandToBrandFineResponesDto(Brand brand);

    Brand brandCreateRequestDtoToBrand(BrandCreateRequestDto brandCreateRequestDto);

    BrandCreateResponseDto brandToBrandCreateResponseDto(Brand brand);

    Brand brandUpdateRequestDtoToBrand(BrandUpdateRequestDto brandUpdateRequestDto);

    BrandUpdateResponseDto brandToBrandUpdateResponseDto(Brand brand);
}
