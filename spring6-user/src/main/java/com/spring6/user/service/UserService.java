package com.spring6.user.service;

import com.spring6.common.dto.BrandFindResponseDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<BrandFindResponseDto> findAll();

    List<BrandFindResponseDto> findByPage(Integer pageNumber, Integer perPageCount, String sortField, String sortDir, BrandSearchKeywordEnum searchField, String searchKeyword);

    BrandFindResponseDto findById(UUID id);

    BrandCreateResponseDto create(BrandCreateRequestDto brandCreateRequestDto);

    BrandUpdateResponseDto update(UUID id, BrandUpdateRequestDto brandCreateRequestDto) throws BrandNotFoundException;

    void deleteById(UUID id) throws BrandNotFoundException;

    Boolean isNameExist(String name);

    Boolean isIdExist(UUID uuid);

    String updateImageName(UUID brandId, String fileName);
}
