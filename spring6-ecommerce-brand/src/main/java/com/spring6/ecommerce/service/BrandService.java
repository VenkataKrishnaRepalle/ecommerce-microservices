package com.spring6.ecommerce.service;

import com.spring6.ecommerce.commondto.BrandFineResponesDto;
import com.spring6.ecommerce.dto.BrandCreateRequestDto;
import com.spring6.ecommerce.dto.BrandCreateResponseDto;
import com.spring6.ecommerce.dto.BrandUpdateRequestDto;
import com.spring6.ecommerce.dto.BrandUpdateResponseDto;

import java.util.List;
import java.util.UUID;

public interface BrandService {
    List<BrandFineResponesDto> findAll();
    List<BrandFineResponesDto> findByPage(int pageNumber, String sortField, String sortDir, String keyword);
    BrandFineResponesDto findById(UUID id);
    Boolean isNameExist(String name);

    BrandCreateResponseDto create(BrandCreateRequestDto brandCreateRequestDto);

    BrandUpdateResponseDto update(BrandUpdateRequestDto brandCreateRequestDto);

    void deleteById(UUID id);
}
