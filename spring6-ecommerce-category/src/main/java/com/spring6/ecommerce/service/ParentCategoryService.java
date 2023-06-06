package com.spring6.ecommerce.service;

import com.spring6.ecommerce.common.dto.ParentCategoryFindResponseDto;
import com.spring6.ecommerce.dto.CategoryUpdateRequestDto;
import com.spring6.ecommerce.dto.CategoryUpdateResponseDto;
import com.spring6.ecommerce.dto.ParentCategoryUpdateRequestDto;
import com.spring6.ecommerce.dto.ParentCategoryUpdateResponseDto;

import java.util.List;
import java.util.UUID;

public interface ParentCategoryService {
    List<ParentCategoryFindResponseDto> listAll();
    ParentCategoryFindResponseDto findCategoryById(UUID parentCategoryId);
    ParentCategoryUpdateResponseDto updateParentCategory(UUID id, ParentCategoryUpdateRequestDto parentCategoryUpdateRequestDto);
    void deleteById(UUID id);
}
