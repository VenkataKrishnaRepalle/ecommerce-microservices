package com.pm.spring.ema.ecommerce.mapper;

import com.pm.spring.ema.ecommerce.dto.ProductCreateRequestDto;
import com.pm.spring.ema.ecommerce.dto.ProductImageCreateRequestDto;
import com.pm.spring.ema.ecommerce.dto.ProductImageCreateResponseDto;
import com.pm.spring.ema.ecommerce.dto.ProductImageFindResponseDto;
import com.pm.spring.ema.ecommerce.entity.ProductImage;
import org.mapstruct.Mapper;

@Mapper
public interface ProductImageMapper {

    ProductImageFindResponseDto ProductImageToProductImageFindResponseDto(ProductImage productImage);

    ProductImage ProductImageCreateRequestDtoToProductImage(ProductImageCreateRequestDto productCreateRequestDto);

    ProductCreateRequestDto ProductImageToProductImageCreateRequestDto(ProductImage productImage);

    ProductImage ProductImageCreateResponseDtoToProductImage(ProductImageCreateResponseDto productImageCreateResponseDto);

    ProductImageCreateResponseDto ProductImageToProductImageCreateResponseDto(ProductImage productImage);
}
