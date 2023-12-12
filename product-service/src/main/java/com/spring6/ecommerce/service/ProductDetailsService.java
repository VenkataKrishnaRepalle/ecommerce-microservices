package com.pm.spring.ema.ecommerce.service;

import com.pm.spring.ema.common.dto.ProductDetailsFindResponseDto;

import java.util.List;
import java.util.UUID;

public interface ProductDetailsService {
    List<ProductDetailsFindResponseDto> create(UUID id, String[] detailNames, String[] detailValues);

    List<ProductDetailsFindResponseDto> update(UUID id, String[] detailName, String[] detailValue);

    List<ProductDetailsFindResponseDto> getByProductId(UUID id);
}
