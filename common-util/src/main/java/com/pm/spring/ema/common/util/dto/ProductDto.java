package com.pm.spring.ema.common.util.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

  private UUID id;

  private String name;

  private String alias;

  private String shortDescription;

  private String fullDescription;

  private Boolean inStock;

  private BigDecimal cost;

  private BigDecimal price;

  private BigDecimal discountPercent;

  private Float length;

  private Float width;

  private Float weight;

  private Float height;

  private String mainImage;

  private UUID brandId;

  private Boolean isEnabled;

  private Date createdTime;

  private Date updatedTime;
}
