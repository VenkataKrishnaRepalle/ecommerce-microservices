package com.pm.spring.ema.product.service;

import com.pm.spring.ema.common.util.dto.ProductDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {

  List<ProductDto> listAll();

  ProductDto getProductById(UUID id);

  ProductDto create(ProductDto productDto);

  ProductDto update(UUID id, ProductDto productDto);

  void updateProductStatusById(UUID id, boolean status);

  void deleteProductById(UUID id);

  boolean isProductNameExists(String productName);

  boolean isProductExists(UUID id);

  void uploadImage(UUID id, String fileName);

  List<ProductDto> findByPage(
      int pageNumber, String sortField, String sortDir, String keyword);

  List<ProductDto> getByCategoryId(UUID categoryId);

  List<ProductDto> getByBrandId(UUID brandId);

  List<ProductDto> getByCategoryIdAndBrandId(UUID categoryId, UUID brandId);

  Boolean isProductExistsById(UUID productId);
}
