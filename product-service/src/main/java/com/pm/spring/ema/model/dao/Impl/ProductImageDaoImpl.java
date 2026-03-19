package com.pm.spring.ema.model.dao.Impl;

import com.pm.spring.ema.model.dao.ProductImageDao;
import com.pm.spring.ema.model.entity.ProductImage;
import com.pm.spring.ema.model.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductImageDaoImpl implements ProductImageDao {
  private final ProductImageRepository productImageRepository;

  @Override
  public ProductImage save(ProductImage productImage) {
    return productImageRepository.save(productImage);
  }
}
