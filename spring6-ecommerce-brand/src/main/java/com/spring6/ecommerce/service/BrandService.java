package com.spring6.ecommerce.service;

import com.spring6.ecommerce.commondto.BrandFineResponesDto;
import com.spring6.ecommerce.dto.BrandCreateRequestDto;
import com.spring6.ecommerce.dto.BrandCreateResponseDto;
import com.spring6.ecommerce.dto.BrandUpdateRequestDto;
import com.spring6.ecommerce.dto.BrandUpdateResponseDto;

import java.util.List;
import java.util.UUID;

public interface BrandService {
    List<BrandFineResponesDto> listAll();

    BrandFineResponesDto getById(UUID id);

    BrandCreateResponseDto save(BrandCreateRequestDto brandCreateRequestDto);

    BrandUpdateResponseDto update(BrandUpdateRequestDto brandCreateRequestDto);

    void deleteById(UUID id);
}
