package com.spring6.ecommerce.service;

import com.spring6.common.dto.SubCategoryFindResponseDto;
import com.spring6.ecommerce.dto.SubCategoryCreateRequestDto;
import com.spring6.ecommerce.dto.SubCategoryCreateResponseDto;
import com.spring6.ecommerce.dto.SubCategoryUpdateRequestDto;
import com.spring6.ecommerce.dto.SubCategoryUpdateResponseDto;

import java.util.List;
import java.util.UUID;

public interface SubCategoryService {
    List<SubCategoryFindResponseDto> getAllSubCategory();

    SubCategoryFindResponseDto getSubCategoryById(UUID id);

    SubCategoryUpdateResponseDto updateSubCategory(UUID id, SubCategoryUpdateRequestDto subCategoryUpdateRequestDto);

    void deleteSubCategoryById(UUID categoryId);

    SubCategoryCreateResponseDto createSubCategory(SubCategoryCreateRequestDto subCategoryCreateRequestDto);

    Boolean isSubCategoryExistByName(String name);

    List<SubCategoryFindResponseDto> findByCategoryId(UUID categoryId);

    List<SubCategoryFindResponseDto> findByPage(int pageNumber, String sortField, String sortDir, String keyword);

    boolean isSubCategoryExistById(UUID id);

    String updateSubCategoryImageById(UUID id, String fileName);
}
