package com.spring6.order.dto.mapper;

import com.spring6.order.dto.request.OrderCreateRequestDto;
import com.spring6.order.dto.request.OrderUpdateRequestDto;
import com.spring6.order.dto.response.OrderAuditResponseDto;
import com.spring6.order.dto.response.OrderCreateResponseDto;
import com.spring6.order.dto.response.OrderUpdateResponseDto;
import com.spring6.order.model.entity.Order;
import com.spring6.order.utils.InstantFormatter;
import com.spring6.common.dto.BrandFindResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;

@Mapper
public interface OrderMapper {

    @Mapping(source = "createdOn", target = "createdOn", qualifiedByName = "formatInstant")
    @Mapping(source = "lastUpdatedOn", target = "lastUpdatedOn", qualifiedByName = "formatInstant")
    BrandFindResponseDto brandToBrandFindResponseDto(Order order);

    Order brandCreateRequestDtoToBrand(OrderCreateRequestDto orderCreateRequestDto);

    OrderCreateResponseDto brandToBrandCreateResponseDto(Order order);

    Order brandUpdateRequestDtoToBrand(OrderUpdateRequestDto orderUpdateRequestDto);

    OrderUpdateResponseDto brandToBrandUpdateResponseDto(Order order);

    @Mapping(source = "createdOn", target = "createdOn", qualifiedByName = "formatInstant")
    @Mapping(source = "lastUpdatedOn", target = "lastUpdatedOn", qualifiedByName = "formatInstant")
    OrderAuditResponseDto brandToBrandAuditResponseDto(Order order);

    @Named("formatInstant")
    default String formatInstant(Instant instant) {
        if (instant != null) {
            return InstantFormatter.formatInstant(instant);
        }
        return null;
    }

}
