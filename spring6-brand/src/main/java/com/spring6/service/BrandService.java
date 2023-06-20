package com.spring6.service;

import com.spring6.common.dto.BrandFindResponesDto;
import com.spring6.dto.BrandCreateRequestDto;
import com.spring6.dto.BrandCreateResponseDto;
import com.spring6.dto.BrandUpdateRequestDto;
import com.spring6.dto.BrandUpdateResponseDto;
import com.spring6.enums.BrandSearchKeywordEnum;
import com.spring6.exception.BrandNotFoundException;

import java.util.List;
import java.util.UUID;

public interface BrandService {
    List<BrandFindResponesDto> findAll();

    List<BrandFindResponesDto> findByPage(Integer pageNumber, Integer perPageCount, String sortField, String sortDir, BrandSearchKeywordEnum searchField, String searchKeyword);

    BrandFindResponesDto findById(UUID id);

    BrandCreateResponseDto create(BrandCreateRequestDto brandCreateRequestDto);

    BrandUpdateResponseDto update(UUID id, BrandUpdateRequestDto brandCreateRequestDto) throws BrandNotFoundException;

    void deleteById(UUID id) throws BrandNotFoundException;

    Boolean isNameExist(String name);

    Boolean isIdExist(UUID uuid);

    void updateImageName(UUID brandId, String fileName);
}
