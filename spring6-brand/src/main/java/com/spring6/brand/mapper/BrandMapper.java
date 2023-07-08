package com.spring6.brand.mapper;

import com.spring6.brand.dto.*;
import com.spring6.brand.entity.Brand;
import com.spring6.brand.utils.InstantFormatter;
import com.spring6.common.dto.BrandFindResponseDto;
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
