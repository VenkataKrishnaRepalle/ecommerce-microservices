package com.spring6.ecommerce.service;

import com.spring6.ecommerce.common.dto.SubCategoryFindResponseDto;
import com.spring6.ecommerce.dto.SubCategoryCreateRequestDto;
import com.spring6.ecommerce.dto.SubCategoryCreateResponseDto;
import com.spring6.ecommerce.dto.SubCategoryUpdateRequestDto;
import com.spring6.ecommerce.dto.SubCategoryUpdateResponseDto;
import com.spring6.ecommerce.entity.SubCategory;

import java.util.List;
import java.util.UUID;

public interface SubCategoryService {
    List<SubCategoryFindResponseDto> findAll();

    SubCategoryFindResponseDto findById(UUID id);

    SubCategoryUpdateResponseDto update(UUID id, SubCategoryUpdateRequestDto subCategoryUpdateRequestDto);

    void deleteById(UUID categoryId);

    SubCategoryCreateResponseDto create(SubCategoryCreateRequestDto subCategoryCreateRequestDto);

    Boolean isSubCategoryExistByName(String name);

    List<SubCategoryFindResponseDto> findByCategoryId(UUID categoryId);

    List<SubCategoryFindResponseDto> findByPage(int pageNumber, String sortField, String sortDir, String keyword);

    boolean isSubCategoryExistById(UUID id);

    void updateFileNameById(UUID id, String fileName);
}
