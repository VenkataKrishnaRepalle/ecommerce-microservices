package com.pm.spring.ema.product.service;

import com.pm.spring.ema.common.util.dto.ProductDetailsDto;

import java.util.List;
import java.util.UUID;

public interface ProductDetailsService {
  List<ProductDetailsDto> create(UUID id, String[] detailNames, String[] detailValues);

  List<ProductDetailsDto> update(UUID id, String[] detailName, String[] detailValue);

  List<ProductDetailsDto> getByProductId(UUID id);
}
