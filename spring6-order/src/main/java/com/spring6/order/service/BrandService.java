package com.spring6.order.service;

import com.spring6.order.dto.request.BrandCreateRequestDto;
import com.spring6.order.dto.response.BrandCreateResponseDto;
import com.spring6.order.dto.request.BrandUpdateRequestDto;
import com.spring6.order.dto.response.BrandUpdateResponseDto;
import com.spring6.order.dto.enums.BrandSearchKeywordEnum;
import com.spring6.order.exception.BrandNotFoundException;
import com.spring6.common.dto.BrandFindResponseDto;

import java.util.List;
import java.util.UUID;

public interface BrandService {
    List<BrandFindResponseDto> getAll();

    List<BrandFindResponseDto> getByPage(Integer pageNumber, Integer perPageCount, String sortField, String sortDir, BrandSearchKeywordEnum searchField, String searchKeyword);

    BrandFindResponseDto getById(UUID id);

    BrandCreateResponseDto create(BrandCreateRequestDto brandCreateRequestDto);

    BrandUpdateResponseDto update(UUID id, BrandUpdateRequestDto brandCreateRequestDto) throws BrandNotFoundException;

    void deleteById(UUID id) throws BrandNotFoundException;

    Boolean isIdExist(UUID uuid);

    String updateImageName(UUID brandId, String fileName);

    String getImageNameById(UUID id);
}
