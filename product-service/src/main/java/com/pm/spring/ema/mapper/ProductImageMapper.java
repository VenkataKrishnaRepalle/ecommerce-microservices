package com.pm.spring.ema.mapper;

import com.pm.spring.ema.dto.ProductCreateRequestDto;
import com.pm.spring.ema.dto.ProductImageCreateRequestDto;
import com.pm.spring.ema.dto.ProductImageCreateResponseDto;
import com.pm.spring.ema.dto.ProductImageFindResponseDto;
import com.pm.spring.ema.model.entity.ProductImage;
import org.mapstruct.Mapper;

@Mapper
public interface ProductImageMapper {

    ProductImageFindResponseDto ProductImageToProductImageFindResponseDto(ProductImage productImage);

    ProductImage ProductImageCreateRequestDtoToProductImage(ProductImageCreateRequestDto productCreateRequestDto);

    ProductCreateRequestDto ProductImageToProductImageCreateRequestDto(ProductImage productImage);

    ProductImage ProductImageCreateResponseDtoToProductImage(ProductImageCreateResponseDto productImageCreateResponseDto);

    ProductImageCreateResponseDto ProductImageToProductImageCreateResponseDto(ProductImage productImage);
}
