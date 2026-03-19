package com.pm.spring.ema.model.dao;

import com.pm.spring.ema.model.entity.ProductDetails;
import java.util.List;
import java.util.UUID;

public interface ProductDetailsDao {
  ProductDetails isProductDetailsExists(UUID productId, String name);

  ProductDetails save(ProductDetails productDetails);

  List<ProductDetails> getByProductId(UUID id);

  Boolean isProductDetailsExistsWithProductId(UUID id);
}
