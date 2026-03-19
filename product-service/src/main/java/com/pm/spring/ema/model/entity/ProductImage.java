package com.pm.spring.ema.model.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Data
@Table(name = "product_images")
@RequiredArgsConstructor
public class ProductImage {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @JdbcTypeCode(SqlTypes.CHAR)
  @Column(columnDefinition = "varchar(36)", updatable = false, nullable = false)
  private UUID id;

  @Column(nullable = false)
  private String name;

  // todo: imageOrder -> Integer, Status -> Active, Disable
  @JdbcTypeCode(SqlTypes.CHAR)
  @Column(columnDefinition = "varchar(36)", nullable = false)
  private UUID productId;
}
