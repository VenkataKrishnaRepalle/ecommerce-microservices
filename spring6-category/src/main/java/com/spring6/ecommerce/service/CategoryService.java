package com.spring6.ecommerce.service;



import com.spring6.common.dto.CategoryFindResponseDto;
import com.spring6.ecommerce.dto.CategoryCreateRequestDto;
import com.spring6.ecommerce.dto.CategoryCreateResponseDto;
import com.spring6.ecommerce.dto.CategoryUpdateRequestDto;
import com.spring6.ecommerce.dto.CategoryUpdateResponseDto;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryCreateResponseDto createCategory(CategoryCreateRequestDto categoryCreateRequestDto);

    List<CategoryFindResponseDto> getAllCategory();

    CategoryFindResponseDto getCategoryById(UUID id);

    CategoryUpdateResponseDto updateCategory(UUID id, CategoryUpdateRequestDto categoryUpdateRequestDto);

    void deleteCategoryById(UUID id);

    Boolean isCategoryExistByName(String name);
    boolean isCategoryExistById(UUID id);


    List<CategoryFindResponseDto> findByPage(int pageNumber, String sortField, String sortDir, String keyword);

    String updateImageById(UUID id, String fileName);
    String getCategoryImageNameById(UUID id);
}
