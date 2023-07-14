package com.spring6.order.service;

import com.spring6.order.dto.request.OrderCreateRequestDto;
import com.spring6.order.dto.request.OrderUpdateRequestDto;
import com.spring6.order.dto.response.OrderCreateResponseDto;
import com.spring6.order.dto.response.OrderUpdateResponseDto;
import com.spring6.order.dto.enums.OrderSearchKeywordEnum;
import com.spring6.order.exception.OrderNotFoundException;
import com.spring6.common.dto.BrandFindResponseDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    List<BrandFindResponseDto> getAll();

    List<BrandFindResponseDto> getByPage(Integer pageNumber, Integer perPageCount, String sortField, String sortDir, OrderSearchKeywordEnum searchField, String searchKeyword);

    BrandFindResponseDto getById(UUID id);

    OrderCreateResponseDto create(OrderCreateRequestDto orderCreateRequestDto);

    OrderUpdateResponseDto update(UUID id, OrderUpdateRequestDto brandCreateRequestDto) throws OrderNotFoundException;

    void deleteById(UUID id) throws OrderNotFoundException;

    Boolean isIdExist(UUID uuid);

    String updateImageName(UUID brandId, String fileName);

    String getImageNameById(UUID id);
}
