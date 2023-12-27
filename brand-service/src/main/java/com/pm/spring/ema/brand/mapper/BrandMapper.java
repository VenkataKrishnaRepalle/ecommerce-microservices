package com.pm.spring.ema.brand.mapper;

import com.pm.spring.ema.brand.dto.request.BrandCreateRequestDto;
import com.pm.spring.ema.brand.dto.request.BrandUpdateRequestDto;
import com.pm.spring.ema.brand.dto.response.BrandAuditResponseDto;
import com.pm.spring.ema.brand.dto.response.BrandCreateResponseDto;
import com.pm.spring.ema.brand.dto.response.BrandUpdateResponseDto;
import com.pm.spring.ema.brand.model.entity.Brand;
import com.pm.spring.ema.common.dto.BrandFindResponseDto;
import com.pm.spring.ema.common.util.InstantFormatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;

@Mapper
public interface BrandMapper {

    @Mapping(source = "createdOn", target = "createdOn", qualifiedByName = "formatInstant")
    @Mapping(source = "lastUpdatedOn", target = "lastUpdatedOn", qualifiedByName = "formatInstant")
    BrandFindResponseDto brandToBrandFindResponseDto(Brand brand);

    Brand brandCreateRequestDtoToBrand(BrandCreateRequestDto brandCreateRequestDto);

    BrandCreateResponseDto brandToBrandCreateResponseDto(Brand brand);

    Brand brandUpdateRequestDtoToBrand(BrandUpdateRequestDto brandUpdateRequestDto);

    BrandUpdateResponseDto brandToBrandUpdateResponseDto(Brand brand);

    @Mapping(source = "createdOn", target = "createdOn", qualifiedByName = "formatInstant")
    @Mapping(source = "lastUpdatedOn", target = "lastUpdatedOn", qualifiedByName = "formatInstant")
    BrandAuditResponseDto brandToBrandAuditResponseDto(Brand brand);

    @Named("formatInstant")
    default String formatInstant(Instant instant) {
        if (instant != null) {
            return InstantFormatter.formatInstant(instant);
        }
        return null;
    }

}
