package com.pm.spring.ema.ecommerce.service;



import com.pm.spring.ema.common.dto.CategoryFindResponseDto;
import com.pm.spring.ema.ecommerce.dto.CategoryCreateRequestDto;
import com.pm.spring.ema.ecommerce.dto.CategoryCreateResponseDto;
import com.pm.spring.ema.ecommerce.dto.CategoryUpdateRequestDto;
import com.pm.spring.ema.ecommerce.dto.CategoryUpdateResponseDto;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryCreateResponseDto createCategory(CategoryCreateRequestDto categoryCreateRequestDto);

    List<CategoryFindResponseDto> getAllCategory();

    CategoryFindResponseDto getCategoryById(UUID id);

    CategoryUpdateResponseDto updateCategory(UUID id, CategoryUpdateRequestDto categoryUpdateRequestDto);

    void deleteCategoryById(UUID id);

    Boolean isCategoryExistByName(String name);
    Boolean isCategoryExistById(UUID id);


    List<CategoryFindResponseDto> findByPage(int pageNumber, String sortField, String sortDir, String keyword);

    String updateImageById(UUID id, String fileName);
    String getCategoryImageNameById(UUID id);
}
