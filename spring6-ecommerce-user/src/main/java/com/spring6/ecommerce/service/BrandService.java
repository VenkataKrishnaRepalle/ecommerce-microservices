package com.spring6.ecommerce.service;

import com.spring6.ecommerce.commonutil.dto.BrandFineResponesDto;
import com.spring6.ecommerce.dto.BrandCreateRequestDto;
import com.spring6.ecommerce.dto.BrandCreateResponseDto;
import com.spring6.ecommerce.dto.BrandUpdateRequestDto;
import com.spring6.ecommerce.dto.BrandUpdateResponseDto;
import com.spring6.ecommerce.exception.BrandNotFoundException;

import java.util.List;
import java.util.UUID;

public interface BrandService {
    List<BrandFineResponesDto> findAll();
    List<BrandFineResponesDto> findByPage(int pageNumber, String sortField, String sortDir, String keyword);
    BrandFineResponesDto findById(UUID id);
    Boolean isNameExist(String name);

    BrandCreateResponseDto create(BrandCreateRequestDto brandCreateRequestDto);

    BrandUpdateResponseDto update(UUID id, BrandUpdateRequestDto brandCreateRequestDto)  throws BrandNotFoundException;

    void deleteById(UUID id) throws BrandNotFoundException;
}
