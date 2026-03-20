package com.pm.spring.ema.product.mapper;

import com.pm.spring.ema.common.util.dto.ProductDto;
import com.pm.spring.ema.product.model.entity.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {

  ProductDto toProductDto(Product product);

  Product toProduct(ProductDto productDto);
}
