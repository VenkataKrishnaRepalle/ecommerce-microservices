package com.pm.spring.ema.model.dao.Impl;

import com.pm.spring.ema.model.dao.ProductDetailsDao;
import com.pm.spring.ema.model.entity.ProductDetails;
import com.pm.spring.ema.model.repository.ProductDetailsRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ProductDetailsDaoImpl implements ProductDetailsDao {

  private final ProductDetailsRepository productDetailsRepository;

  @Override
  public ProductDetails isProductDetailsExists(UUID productId, String name) {
    return productDetailsRepository.isProductDetailsExists(productId, name);
  }

  @Override
  public ProductDetails save(ProductDetails productDetails) {
    return productDetailsRepository.save(productDetails);
  }

  @Override
  public List<ProductDetails> getByProductId(UUID id) {
    return productDetailsRepository.getByProductId(id);
  }

  @Override
  public Boolean isProductDetailsExistsWithProductId(UUID id) {
    return productDetailsRepository.isProductDetailsExistsWithProductId(id);
  }
}
