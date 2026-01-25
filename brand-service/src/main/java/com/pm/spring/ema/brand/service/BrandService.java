package com.pm.spring.ema.brand.service;

import com.pm.spring.ema.brand.dto.request.BrandCreateRequestDto;
import com.pm.spring.ema.brand.dto.response.BrandCreateResponseDto;
import com.pm.spring.ema.brand.dto.request.BrandUpdateRequestDto;
import com.pm.spring.ema.brand.dto.response.BrandUpdateResponseDto;
import com.pm.spring.ema.brand.dto.enums.BrandSearchKeywordEnum;
import com.pm.spring.ema.common.util.dto.BrandFindResponseDto;
import com.pm.spring.ema.common.util.exception.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface BrandService {
    List<BrandFindResponseDto> getAll();

    List<BrandFindResponseDto> getByPage(Integer pageNumber, Integer perPageCount, String sortField, String sortDir, BrandSearchKeywordEnum searchField, String searchKeyword);

    BrandFindResponseDto getById(UUID id);

    BrandCreateResponseDto create(BrandCreateRequestDto brandCreateRequestDto);

    BrandUpdateResponseDto update(UUID id, BrandUpdateRequestDto brandCreateRequestDto) throws NotFoundException;

    void deleteById(UUID id) throws NotFoundException;

    Boolean isIdExist(UUID uuid);

    String updateImageName(UUID brandId, String fileName);

    String getImageNameById(UUID id);
}
