package com.spring6.order.service;

import com.pm.spring.ema.order.dto.enums.OrderSearchKeyword;
import com.spring6.order.dto.request.OrderCreateRequestDto;
import com.spring6.order.dto.request.OrderUpdateRequestDto;
import com.spring6.order.dto.response.OrderCreateResponseDto;
import com.spring6.order.dto.response.OrderResponseDto;
import com.spring6.order.dto.response.OrderUpdateResponseDto;
import com.spring6.order.exception.OrderNotFoundException;

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
