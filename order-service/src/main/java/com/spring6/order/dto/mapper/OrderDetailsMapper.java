package com.spring6.order.dto.mapper;

import com.spring6.order.dto.request.OrderDetailsCreateRequestDto;
import com.spring6.order.model.entity.OrderDetail;
import org.mapstruct.Mapper;

@Mapper
public interface OrderDetailsMapper {
    OrderDetail orderDetailCreateRequestDtoToOrderDetail(OrderDetailsCreateRequestDto orderDetailsCreateRequestDto);
}
