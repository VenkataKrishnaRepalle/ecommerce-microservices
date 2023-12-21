package com.pm.spring.ema.category.service;

import com.pm.spring.ema.category.common.dto.subcategoryDto.request.SubCategoryCreateRequestDto;
import com.pm.spring.ema.category.common.dto.subcategoryDto.request.SubCategoryUpdateRequestDto;
import com.pm.spring.ema.category.common.dto.subcategoryDto.response.SubCategoryCreateResponseDto;
import com.pm.spring.ema.category.common.dto.subcategoryDto.response.SubCategoryDeleteResponseDto;
import com.pm.spring.ema.category.common.dto.subcategoryDto.response.SubCategoryFindResponseDto;
import com.pm.spring.ema.category.common.dto.subcategoryDto.response.SubCategoryUpdateResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface SubCategoryService {

    List<SubCategoryFindResponseDto> getAllSubCategory();

    SubCategoryFindResponseDto getSubCategoryById(UUID id);

    SubCategoryUpdateResponseDto updateSubCategory(UUID id, SubCategoryUpdateRequestDto subCategoryUpdateRequestDto);

    SubCategoryDeleteResponseDto deleteSubCategoryById(UUID categoryId);

    SubCategoryCreateResponseDto createSubCategory(SubCategoryCreateRequestDto subCategoryCreateRequestDto);

    Boolean isSubCategoryExistByName(String name);

    List<SubCategoryFindResponseDto> getSubCategoriesByCategoryId(UUID categoryId);

    List<SubCategoryFindResponseDto> findByPage(int pageNumber, String sortField, String sortDir, String keyword);

    boolean isSubCategoryExistById(UUID id);

    String updateSubCategoryImageById(UUID id, String fileName);
}
