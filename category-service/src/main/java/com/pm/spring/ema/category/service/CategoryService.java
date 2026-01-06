package com.pm.spring.ema.category.service;

import com.pm.spring.ema.common.util.dto.CategoryDto;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryRequestDto);

    List<CategoryDto> getAllCategory();

    CategoryDto getCategoryById(UUID id);

    CategoryDto updateCategory(UUID id, CategoryDto CategoryRequestDto);

    CategoryDto deleteCategoryById(UUID id);

    Boolean isCategoryExistByName(String name);

    Boolean isCategoryExistById(UUID id);

    List<CategoryDto> findByPage(int pageNumber, String sortField, String sortDir, String keyword);

    String updateImageById(UUID id, String fileName);

    String getCategoryImageNameById(UUID id);
}
