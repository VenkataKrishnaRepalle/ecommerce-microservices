package com.spring6.ecommerce.service;

import com.spring6.ecommerce.common.dto.ProductDetailsFindResponseDto;

import java.util.List;
import java.util.UUID;

public interface ProductDetailsService {
    List<ProductDetailsFindResponseDto> create(UUID id, String[] detailNames, String[] detailValues);

    List<ProductDetailsFindResponseDto> update(UUID id, String[] detailName, String[] detailValue);

    List<ProductDetailsFindResponseDto> getByProductId(UUID id);
}
