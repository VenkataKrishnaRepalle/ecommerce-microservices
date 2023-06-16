package com.spring6.ecommerce.service;

import com.spring6.ecommerce.common.dto.ParentCategoryFindResponseDto;
import com.spring6.ecommerce.dto.*;

import java.util.List;
import java.util.UUID;

public interface ParentCategoryService {
    List<ParentCategoryFindResponseDto> listAll();
    ParentCategoryFindResponseDto findCategoryById(UUID parentCategoryId);
    ParentCategoryUpdateResponseDto updateParentCategory(UUID id, ParentCategoryUpdateRequestDto parentCategoryUpdateRequestDto);
    void deleteById(UUID id);
    Boolean isNameExist(String name);
    ParentCategoryCreateResponseDto createParentCategories(ParentCategoryCreateRequestDto parentCategoryCreateRequestDto);
}
