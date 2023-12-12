package com.pm.spring.ema.order.dto.mapper;

import com.pm.spring.ema.order.dto.request.OrderCreateRequestDto;
import com.pm.spring.ema.order.dto.request.OrderUpdateRequestDto;
import com.pm.spring.ema.order.dto.response.OrderAuditResponseDto;
import com.pm.spring.ema.order.dto.response.OrderCreateResponseDto;
import com.pm.spring.ema.order.dto.response.OrderResponseDto;
import com.pm.spring.ema.order.dto.response.OrderUpdateResponseDto;
import com.pm.spring.ema.order.model.entity.Order;
import com.pm.spring.ema.order.utils.InstantFormatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;

@Mapper
public interface OrderMapper {

    @Mapping(source = "createdOn", target = "createdOn", qualifiedByName = "formatInstant")
    @Mapping(source = "lastUpdatedOn", target = "lastUpdatedOn", qualifiedByName = "formatInstant")
    OrderResponseDto orderToOrderResponseDto(Order order);

    Order orderCreateRequestDtoToOrder(OrderCreateRequestDto orderCreateRequestDto);

    OrderCreateResponseDto orderToOrderCreateResponseDto(Order order);

    Order orderUpdateRequestDtoToOrder(OrderUpdateRequestDto orderUpdateRequestDto);

    OrderUpdateResponseDto orderToOrderUpdateResponseDto(Order order);

    @Mapping(source = "createdOn", target = "createdOn", qualifiedByName = "formatInstant")
    @Mapping(source = "lastUpdatedOn", target = "lastUpdatedOn", qualifiedByName = "formatInstant")
    OrderAuditResponseDto orderToOrderAuditResponseDto(Order order);

    @Named("formatInstant")
    default String formatInstant(Instant instant) {
        if (instant != null) {
            return InstantFormatter.formatInstant(instant);
        }
        return null;
    }

}
