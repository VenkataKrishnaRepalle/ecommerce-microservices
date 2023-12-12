package com.pm.spring.ema.order.service;

import com.pm.spring.ema.order.dto.request.OrderCreateRequestDto;
import com.pm.spring.ema.order.dto.request.OrderUpdateRequestDto;
import com.pm.spring.ema.order.dto.response.OrderCreateResponseDto;
import com.pm.spring.ema.order.dto.response.OrderResponseDto;
import com.pm.spring.ema.order.dto.response.OrderUpdateResponseDto;
import com.pm.spring.ema.order.dto.enums.OrderSearchKeyword;
import com.pm.spring.ema.order.exception.OrderNotFoundException;
import java.util.List;
import java.util.UUID;

public interface OrderService {
    List<OrderResponseDto> getAll();

    List<OrderResponseDto> getByPage(Integer pageNumber, Integer perPageCount, String sortField, String sortDir, OrderSearchKeyword searchField, String searchKeyword);

    OrderResponseDto getById(UUID id);

    OrderCreateResponseDto create(OrderCreateRequestDto orderCreateRequestDto);

    OrderUpdateResponseDto update(UUID id, OrderUpdateRequestDto brandCreateRequestDto) throws OrderNotFoundException;

    void deleteById(UUID id) throws OrderNotFoundException;

}
