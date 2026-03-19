package com.pm.spring.ema.common.util.dto;

import java.util.Date;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductFindResponseDto {

  private UUID id;

  private String name;

  private String alias;

  private String shortDescription;

  private String fullDescription;

  private Boolean inStock;

  private Float cost;

  private Float price;

  private Float discountPercent;

  private Float length;

  private Float width;

  private Float weight;

  private Float height;

  private String mainImage;

  private UUID categoryId;

  private UUID brandId;

  private Boolean isEnabled;

  private Date createdTime;

  private Date updatedTime;
}
