package com.pm.spring.ema.ecommerce.mapper;

import com.pm.spring.ema.common.dto.SubCategoryFindResponseDto;
import com.pm.spring.ema.ecommerce.dto.SubCategoryCreateRequestDto;
import com.pm.spring.ema.ecommerce.dto.SubCategoryCreateResponseDto;
import com.pm.spring.ema.ecommerce.dto.SubCategoryUpdateRequestDto;
import com.pm.spring.ema.ecommerce.dto.SubCategoryUpdateResponseDto;
import com.pm.spring.ema.ecommerce.entity.SubCategory;
import org.mapstruct.Mapper;

@Mapper
public interface SubCategoryMapper {
    SubCategoryFindResponseDto subCategoryToSubCategoryFindResponseDto(SubCategory subCategory);

    SubCategory subCategoryUpdateRequestDtoToSubCategory(SubCategoryUpdateRequestDto subCategoryUpdateRequestDto);

    SubCategoryUpdateResponseDto subCategoryToSubCategoryUpdateResponseDto(SubCategory subCategory);

    SubCategoryCreateResponseDto subCategoryToSubCategoryCreateResponseDto(SubCategory subCategory);

    SubCategory subCategoryCreateRequestDtoToSubCategory(SubCategoryCreateRequestDto subCategoryCreateRequestDto);
}
