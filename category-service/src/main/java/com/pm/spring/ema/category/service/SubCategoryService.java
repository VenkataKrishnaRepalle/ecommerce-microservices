package com.pm.spring.ema.category.service;

import com.pm.spring.ema.common.dto.SubCategoryFindResponseDto;
import com.pm.spring.ema.category.dto.subcategoryDto.SubCategoryCreateRequestDto;
import com.pm.spring.ema.category.dto.subcategoryDto.SubCategoryCreateResponseDto;
import com.pm.spring.ema.category.dto.subcategoryDto.SubCategoryUpdateRequestDto;
import com.pm.spring.ema.category.dto.subcategoryDto.SubCategoryUpdateResponseDto;

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
