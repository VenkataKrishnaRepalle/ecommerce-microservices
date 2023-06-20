package com.spring6.ecommerce.service;

import com.spring6.ecommerce.common.dto.brand.BrandFindResponesDto;
import com.spring6.ecommerce.dto.BrandCreateRequestDto;
import com.spring6.ecommerce.dto.BrandCreateResponseDto;
import com.spring6.ecommerce.dto.BrandUpdateRequestDto;
import com.spring6.ecommerce.dto.BrandUpdateResponseDto;
import com.spring6.ecommerce.exception.BrandNotFoundException;

import java.util.List;
import java.util.UUID;

public interface BrandService {
    List<BrandFindResponesDto> findAll();

    List<BrandFindResponesDto> findByPage(Integer pageNumber, Integer perPageCount, String sortField, String sortDir, String keyword);

    BrandFindResponesDto findById(UUID id);

    BrandCreateResponseDto create(BrandCreateRequestDto brandCreateRequestDto);

    BrandUpdateResponseDto update(UUID id, BrandUpdateRequestDto brandCreateRequestDto) throws BrandNotFoundException;

    void deleteById(UUID id) throws BrandNotFoundException;

    Boolean isNameExist(String name);

    Boolean isIdExist(UUID uuid);

    void updateImageName(UUID brandId, String fileName);
}
