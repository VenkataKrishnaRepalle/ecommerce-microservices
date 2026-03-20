package com.pm.spring.ema.product.mapper;

import com.pm.spring.ema.common.util.dto.ProductDetailsDto;
import com.pm.spring.ema.product.model.entity.ProductDetails;
import org.mapstruct.Mapper;

@Mapper
public interface ProductDetailsMapper {
    ProductDetailsDto toProductDetailsDto(ProductDetails productDetails);

    ProductDetails toProductDetails(ProductDetailsDto productDetailsDto);

}
