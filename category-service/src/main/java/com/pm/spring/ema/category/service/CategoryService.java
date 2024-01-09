package com.pm.spring.ema.category.service;


import com.pm.spring.ema.category.common.dto.categoryDto.request.CategoryCreateRequestDto;
import com.pm.spring.ema.category.common.dto.categoryDto.request.CategoryUpdateRequestDto;
import com.pm.spring.ema.category.common.dto.categoryDto.response.CategoryCreateResponseDto;
import com.pm.spring.ema.category.common.dto.categoryDto.response.CategoryDeleteResponseDto;
import com.pm.spring.ema.category.common.dto.categoryDto.response.CategoryFindResponseDto;
import com.pm.spring.ema.category.common.dto.categoryDto.response.CategoryUpdateResponseDto;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryCreateResponseDto createCategory(CategoryCreateRequestDto categoryCreateRequestDto);

    List<CategoryFindResponseDto> getAllCategory();

    CategoryFindResponseDto getCategoryById(UUID id);

    CategoryUpdateResponseDto updateCategory(UUID id, CategoryUpdateRequestDto categoryUpdateRequestDto);

    CategoryDeleteResponseDto deleteCategoryById(UUID id);

    Boolean isCategoryExistByName(String name);

    Boolean isCategoryExistById(UUID id);

    List<CategoryFindResponseDto> findByPage(int pageNumber, String sortField, String sortDir, String keyword);

    String updateImageById(UUID id, String fileName);

    String getCategoryImageNameById(UUID id);
}
