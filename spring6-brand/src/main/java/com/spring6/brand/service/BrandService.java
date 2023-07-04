package com.spring6.brand.service;

import com.spring6.brand.dto.BrandCreateRequestDto;
import com.spring6.brand.dto.BrandCreateResponseDto;
import com.spring6.brand.dto.BrandUpdateRequestDto;
import com.spring6.brand.dto.BrandUpdateResponseDto;
import com.spring6.common.dto.BrandFindResponseDto;
import com.spring6.brand.enums.BrandSearchKeywordEnum;
import com.spring6.brand.exception.BrandNotFoundException;

import java.util.List;
import java.util.UUID;

public interface BrandService {
    List<BrandFindResponseDto> getAll();

    List<BrandFindResponseDto> getByPage(Integer pageNumber, Integer perPageCount, String sortField, String sortDir, BrandSearchKeywordEnum searchField, String searchKeyword);

    BrandFindResponseDto getById(UUID id);

    BrandCreateResponseDto create(BrandCreateRequestDto brandCreateRequestDto);

    BrandUpdateResponseDto update(UUID id, BrandUpdateRequestDto brandCreateRequestDto) throws BrandNotFoundException;

    void deleteById(UUID id) throws BrandNotFoundException;

    Boolean isNameExist(String name);

    Boolean isIdExist(UUID uuid);

    String updateImageName(UUID brandId, String fileName);

    String getImageNameById(UUID id);
}
