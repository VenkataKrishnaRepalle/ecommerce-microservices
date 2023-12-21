package com.pm.spring.ema.mapper;

import com.pm.spring.ema.common.dto.ProductFindResponseDto;
import com.pm.spring.ema.dto.ProductCreateRequestDto;
import com.pm.spring.ema.dto.ProductCreateResponseDto;
import com.pm.spring.ema.dto.ProductUpdateRequestDto;
import com.pm.spring.ema.dto.ProductUpdateResponseDto;
import com.pm.spring.ema.model.entity.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {

    ProductFindResponseDto productToProductFindResponseDto(Product product);

    Product productCreateRequestDtoToProduct(ProductCreateRequestDto productCreateRequestDto);

    ProductCreateResponseDto productToProductCreateResponseDto(Product product);

    Product productUpdateRequestDtoToProduct(ProductUpdateRequestDto productUpdateRequestDto);

    ProductUpdateResponseDto productToProductUpdateResponseDto(Product product);
}
