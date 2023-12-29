package com.spring6.order.dto.mapper;

import com.pm.spring.ema.order.utils.InstantFormatter;
import com.spring6.order.dto.request.OrderCreateRequestDto;
import com.spring6.order.dto.request.OrderUpdateRequestDto;
import com.spring6.order.dto.response.OrderAuditResponseDto;
import com.spring6.order.dto.response.OrderCreateResponseDto;
import com.spring6.order.dto.response.OrderResponseDto;
import com.spring6.order.dto.response.OrderUpdateResponseDto;
import com.spring6.order.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.time.Instant;

@Mapper
public interface OrderMapper {

//    @Mapping(source = "createdOn", target = "createdOn", qualifiedByName = "formatInstant")
//    @Mapping(source = "lastUpdatedOn", target = "lastUpdatedOn", qualifiedByName = "formatInstant")
    OrderResponseDto orderToOrderResponseDto(Order order);

    Order orderCreateRequestDtoToOrder(OrderCreateRequestDto orderCreateRequestDto);

    OrderCreateResponseDto orderToOrderCreateResponseDto(Order order);

    Order orderUpdateRequestDtoToOrder(OrderUpdateRequestDto orderUpdateRequestDto);

    OrderUpdateResponseDto orderToOrderUpdateResponseDto(Order order);

//    @Mapping(source = "createdOn", target = "createdOn", qualifiedByName = "formatInstant")
//    @Mapping(source = "lastUpdatedOn", target = "lastUpdatedOn", qualifiedByName = "formatInstant")
    OrderAuditResponseDto orderToOrderAuditResponseDto(Order order);

    @Named("formatInstant")
    default String formatInstant(Instant instant) {
        if (instant != null) {
            return InstantFormatter.formatInstant(instant);
        }
        return null;
    }

}
