package com.spring6.ecommerce.service;

import com.spring6.ecommerce.common.dto.CategoryFindResponseDto;
import com.spring6.ecommerce.dto.*;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<CategoryFindResponseDto> listAll();
    CategoryFindResponseDto findCategoryById(UUID parentCategoryId);
    CategoryUpdateResponseDto updateParentCategory(UUID id, CategoryUpdateRequestDto categoryUpdateRequestDto);
    void deleteById(UUID id);
    Boolean isNameExist(String name);
    CategoryCreateResponseDto createParentCategories(CategoryCreateRequestDto categoryCreateRequestDto);
}
