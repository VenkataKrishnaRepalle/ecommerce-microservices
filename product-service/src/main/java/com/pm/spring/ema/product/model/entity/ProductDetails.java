package com.pm.spring.ema.product.model.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_details")
public class ProductDetails {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @JdbcTypeCode(SqlTypes.CHAR)
  @Column(columnDefinition = "varchar(36)", updatable = false, nullable = false)
  private UUID id;

  @Column(length = 255, nullable = false)
  private String name;

  @Column(length = 255, nullable = false)
  private String value;

  @JdbcTypeCode(SqlTypes.CHAR)
  @Column(columnDefinition = "varchar(36)", nullable = false)
  private UUID productId;
}
