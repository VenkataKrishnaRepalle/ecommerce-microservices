package com.pm.spring.ema.brand.service;

import com.pm.spring.ema.brand.dto.BrandDto;
import com.pm.spring.ema.common.util.dto.BrandFindResponseDto;
import com.pm.spring.ema.common.util.exception.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface BrandService {
    List<BrandFindResponseDto> getAll();

    List<BrandFindResponseDto> getByPage(Integer pageNumber, Integer perPageCount, String sortField, String sortDir, String searchField, String searchKeyword);

    BrandFindResponseDto getById(UUID id);

    BrandDto create(BrandDto brandDto);

    BrandDto update(UUID id, BrandDto brandCreateRequestDto) throws NotFoundException;

    void deleteById(UUID id) throws NotFoundException;

    Boolean isIdExist(UUID uuid);

    String updateImageName(UUID brandId, String fileName);

    String getImageNameById(UUID id);
}
