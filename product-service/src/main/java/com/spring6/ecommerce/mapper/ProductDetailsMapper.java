package com.pm.spring.ema.ecommerce.mapper;

import com.pm.spring.ema.common.dto.ProductDetailsFindResponseDto;
import com.pm.spring.ema.ecommerce.dto.ProductDetailsCreateRequestDto;
import com.pm.spring.ema.ecommerce.dto.ProductDetailsUpdateRequestDto;
import com.pm.spring.ema.ecommerce.model.entity.ProductDetails;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ProductDetailsMapper {
    ProductDetailsFindResponseDto productDetailsToProductDetailsFindResponseDto(ProductDetails productDetails);

    ProductDetails productDetailsFindResponseDtoToProductDetails(ProductDetailsFindResponseDto roductDetailsFindResponseDto);

    ProductDetails ProductDetailsCreateRequestDtoToProductDetails(ProductDetailsCreateRequestDto productDetailsCreateRequestDto);

    ProductDetailsCreateRequestDto ProductDetailsToProductDetailsCreateRequestDto(ProductDetails productDetails);

    ProductDetails ProductDetailsUpdateRequestDtoToProductDetails(ProductDetailsUpdateRequestDto productDetailsUpdateRequestDto);

    ProductDetailsUpdateRequestDto ProductDetailsToProductDetailsUpdateRequestDto(ProductDetails productDetails);
}
