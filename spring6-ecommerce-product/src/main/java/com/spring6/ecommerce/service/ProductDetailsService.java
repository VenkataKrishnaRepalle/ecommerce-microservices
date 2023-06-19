package com.spring6.ecommerce.service;

import com.spring6.ecommerce.common.dto.ProductDetailsFindResponseDto;

import java.util.List;
import java.util.UUID;

public interface ProductDetailsService {
    List<ProductDetailsFindResponseDto> addProductDetails(UUID id, String[] detailNames, String[] detailValues);

    List<ProductDetailsFindResponseDto> updateProductDetails(UUID id, String[] detailName, String[] detailValue);
}
