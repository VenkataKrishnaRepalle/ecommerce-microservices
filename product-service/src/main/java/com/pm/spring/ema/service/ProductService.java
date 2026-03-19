package com.pm.spring.ema.service;

import com.pm.spring.ema.common.util.dto.ProductFindResponseDto;
import com.pm.spring.ema.dto.ProductCreateRequestDto;
import com.pm.spring.ema.dto.ProductCreateResponseDto;
import com.pm.spring.ema.dto.ProductUpdateRequestDto;
import com.pm.spring.ema.dto.ProductUpdateResponseDto;
import java.util.List;
import java.util.UUID;

public interface ProductService {

  List<ProductFindResponseDto> listAll();

  ProductFindResponseDto getProductById(UUID id);

  ProductCreateResponseDto create(ProductCreateRequestDto productCreateRequestDto);

  ProductUpdateResponseDto update(UUID id, ProductUpdateRequestDto productUpdateRequestDto);

  void updateProductStatusById(UUID id, boolean status);

  void deleteProductById(UUID id);

  boolean isProductNameExists(String productName);

  boolean isProductExists(UUID id);

  void uploadImage(UUID id, String fileName);

  List<ProductFindResponseDto> findByPage(
      int pageNumber, String sortField, String sortDir, String keyword);

  List<ProductFindResponseDto> getByCategoryId(UUID categoryId);

  List<ProductFindResponseDto> getByBrandId(UUID brandId);

  List<ProductFindResponseDto> getByCategoryIdAndBrandId(UUID categoryId, UUID brandId);

  Boolean isProductExistsById(UUID productId);
}
