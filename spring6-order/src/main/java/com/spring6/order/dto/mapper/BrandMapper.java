package com.spring6.order.dto.mapper;

import com.spring6.order.dto.request.BrandCreateRequestDto;
import com.spring6.order.dto.request.BrandUpdateRequestDto;
import com.spring6.order.dto.response.BrandAuditResponseDto;
import com.spring6.order.dto.response.BrandCreateResponseDto;
import com.spring6.order.dto.response.BrandUpdateResponseDto;
import com.spring6.order.model.entity.Order;
import com.spring6.order.utils.InstantFormatter;
import com.spring6.common.dto.BrandFindResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;

@Mapper
public interface BrandMapper {

    @Mapping(source = "createdOn", target = "createdOn", qualifiedByName = "formatInstant")
    @Mapping(source = "lastUpdatedOn", target = "lastUpdatedOn", qualifiedByName = "formatInstant")
    BrandFindResponseDto brandToBrandFindResponseDto(Order order);

    Order brandCreateRequestDtoToBrand(BrandCreateRequestDto brandCreateRequestDto);

    BrandCreateResponseDto brandToBrandCreateResponseDto(Order order);

    Order brandUpdateRequestDtoToBrand(BrandUpdateRequestDto brandUpdateRequestDto);

    BrandUpdateResponseDto brandToBrandUpdateResponseDto(Order order);

    @Mapping(source = "createdOn", target = "createdOn", qualifiedByName = "formatInstant")
    @Mapping(source = "lastUpdatedOn", target = "lastUpdatedOn", qualifiedByName = "formatInstant")
    BrandAuditResponseDto brandToBrandAuditResponseDto(Order order);

    @Named("formatInstant")
    default String formatInstant(Instant instant) {
        if (instant != null) {
            return InstantFormatter.formatInstant(instant);
        }
        return null;
    }

}
