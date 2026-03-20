package com.pm.spring.ema.product.mapper;

import com.pm.spring.ema.common.util.dto.ProductImageDto;
import com.pm.spring.ema.product.model.entity.ProductImage;
import org.mapstruct.Mapper;

@Mapper
public interface ProductImageMapper {

  ProductImage toProductImage(ProductImageDto productImageDto);

  ProductImageDto toProductImageDto(ProductImage productImage);
}
