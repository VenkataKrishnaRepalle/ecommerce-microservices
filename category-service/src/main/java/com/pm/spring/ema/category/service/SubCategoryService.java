package com.pm.spring.ema.category.service;

import com.pm.spring.ema.common.util.dto.SubCategoryDto;

import java.util.List;
import java.util.UUID;

public interface SubCategoryService {

    List<SubCategoryDto> getAllSubCategory();

    SubCategoryDto getSubCategoryById(UUID id);

    SubCategoryDto updateSubCategory(UUID id, SubCategoryDto subCategoryRequestDto);

    SubCategoryDto deleteSubCategoryById(UUID id);

    SubCategoryDto createSubCategory(SubCategoryDto subCategoryCreateRequestDto);

    Boolean isSubCategoryExistByName(String name);

    List<SubCategoryDto> getSubCategoriesByCategoryId(UUID categoryId);

    List<SubCategoryDto> findByPage(int pageNumber, String sortField, String sortDir, String keyword);

    boolean isSubCategoryExistById(UUID id);

    String updateSubCategoryImageById(UUID id, String fileName);
}
