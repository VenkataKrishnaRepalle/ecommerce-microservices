package com.pm.spring.ema.product.bootstrap;

import com.pm.spring.ema.product.entity.Product;
import com.pm.spring.ema.product.repository.ProductRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

  private final ProductRepository productRepository;

  private static final BigDecimal COST = BigDecimal.valueOf(22999);
  private static final BigDecimal PRICE = BigDecimal.valueOf(19999);

  @Override
  public void run(String... args) {
    loadBrandData();
  }

  private void loadBrandData() {
    if (productRepository.count() == 0) {

      productRepository.save(
          Product.builder()
              .name("OnePlus Nord")
              .alias("CE")
              .shortDescription("very Good Phone")
              .fullDescription("Very good Phone at low price")
              .inStock(true)
              .cost(COST)
              .price(PRICE)
              .discountPercent(discountPercent(COST, PRICE))
              .length(3.5f)
              .width(1.2f)
              .height(6.2f)
              .weight(12.5f)
              .mainImage("11.jpg")
              .brandId(UUID.fromString("a3976462-93b7-4303-b504-0e5b0eddb283"))
              .isEnabled(Boolean.TRUE)
              .build());
      productRepository.save(
          Product.builder()
              .name("Realme")
              .alias("Note 4")
              .shortDescription("very Good Phone")
              .fullDescription("Very good Phone at low price")
              .inStock(true)
              .cost(COST)
              .price(PRICE)
              .discountPercent(discountPercent(COST, PRICE))
              .length(3.5f)
              .width(1.2f)
              .height(6.2f)
              .weight(12.5f)
              .mainImage("11.jpg")
              .brandId(UUID.fromString("a3976462-93b7-4303-b504-0e5b0eddb283"))
              .isEnabled(Boolean.TRUE)
              .build());
      productRepository.save(
          Product.builder()
              .name("Poco")
              .alias("x7")
              .shortDescription("very Good Phone")
              .fullDescription("Very good Phone at low price")
              .inStock(true)
              .cost(COST)
              .price(PRICE)
              .discountPercent(discountPercent(COST, PRICE))
              .length(3.5f)
              .width(1.2f)
              .height(6.2f)
              .weight(12.5f)
              .mainImage("11.jpg")
              .brandId(UUID.fromString("a3976462-93b7-4303-b504-0e5b0eddb283"))
              .isEnabled(Boolean.TRUE)
              .build());
    }
  }

  private BigDecimal discountPercent(BigDecimal cost, BigDecimal price) {
    return cost.subtract(price)
        .multiply(BigDecimal.valueOf(100))
        .divide(cost, 2, RoundingMode.HALF_UP);
  }
}
