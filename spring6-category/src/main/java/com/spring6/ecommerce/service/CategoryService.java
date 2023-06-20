package com.spring6.ecommerce.service;

import com.spring6.ecommerce.common.dto.CategoryFindResponseDto;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryCreateResponseDto create(CategoryCreateRequestDto categoryCreateRequestDto);

    List<CategoryFindResponseDto> findAll();

    CategoryFindResponseDto findById(UUID id);

    CategoryUpdateResponseDto update(UUID id, CategoryUpdateRequestDto categoryUpdateRequestDto);

    void deleteById(UUID id);

    Boolean isCategoryExistByName(String name);
    boolean isCategoryExistById(UUID id);


    List<CategoryFindResponseDto> findByPage(int pageNumber, String sortField, String sortDir, String keyword);

    void updateFileNameById(UUID id, String fileName);
}
