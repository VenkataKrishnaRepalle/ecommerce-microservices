package com.spring6.ecommerce.service;
import com.spring6.ecommerce.commonutil.dto.CategoryFindResponseDto;
import com.spring6.ecommerce.dto.CategoryUpdateResponseDto;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<CategoryFindResponseDto> listAll();
    CategoryFindResponseDto findCategoryById(UUID id);
    BrandUpdateResponseDto update(UUID id, BrandUpdateRequestDto brandCreateRequestDto)  throws BrandNotFoundException;
    CategoryUpdateResponseDto updateCategory(UUID id,CategoryUpdateResponseDto categoryUpdateResponseDto)
}
