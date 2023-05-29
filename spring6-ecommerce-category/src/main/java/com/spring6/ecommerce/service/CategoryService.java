package com.spring6.ecommerce.service;
import com.spring6.ecommerce.commonutil.dto.CategoryFindResponseDto;
import com.spring6.ecommerce.dto.CategoryCreateRequestDto;
import com.spring6.ecommerce.dto.CategoryCreateResponseDto;
import com.spring6.ecommerce.dto.CategoryUpdateRequestDto;
import com.spring6.ecommerce.dto.CategoryUpdateResponseDto;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<CategoryFindResponseDto> listAll();
    CategoryFindResponseDto findCategoryById(UUID id);
    CategoryUpdateResponseDto updateCategory(UUID id, CategoryUpdateRequestDto categoryUpdateRequestDto);

    void deleteCategoryById(UUID categoryId);

    CategoryCreateResponseDto createCategories(CategoryCreateRequestDto categoryCreateRequestDto);
}
