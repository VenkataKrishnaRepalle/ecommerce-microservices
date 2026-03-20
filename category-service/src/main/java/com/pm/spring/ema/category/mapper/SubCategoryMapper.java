package com.pm.spring.ema.category.mapper;

import com.pm.spring.ema.category.model.SubCategory;
import com.pm.spring.ema.common.util.dto.SubCategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubCategoryMapper {

  @Mapping(source = "category.id", target = "categoryUuid")
  SubCategoryDto toSubCategoryDto(SubCategory subCategory);

  @Mapping(source = "categoryUuid", target = "category.id")
  SubCategory toSubCategory(SubCategoryDto subCategory);
}
