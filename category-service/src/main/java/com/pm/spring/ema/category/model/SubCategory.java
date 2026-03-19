package com.pm.spring.ema.category.model;

import com.pm.spring.ema.common.util.enums.SubCategoryEnum;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SubCategory {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
  @JdbcTypeCode(SqlTypes.VARCHAR)
  private UUID id;

  @Column(length = 128, nullable = false, unique = true)
  private String name;

  @Column(length = 64, nullable = false, unique = true)
  private String alias;

  @Column(length = 128, nullable = false)
  private String imageName;

  @Column(name = "status")
  private SubCategoryEnum status;

  @CreationTimestamp private Instant createdOn;

  @UpdateTimestamp private Instant lastUpdatedOn;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;
}
