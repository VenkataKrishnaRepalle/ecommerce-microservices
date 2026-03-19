package com.pm.spring.ema.modal;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "customers")
public class Role {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @JdbcTypeCode(SqlTypes.CHAR)
  @Column(columnDefinition = "varchar(36)", updatable = false, nullable = false)
  @EqualsAndHashCode.Include
  private UUID id;

  @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
  @Builder.Default
  private Set<Customer> customers = new HashSet<>();

  @Enumerated(EnumType.STRING)
  @Column(unique = true, nullable = false)
  private RoleType roleType;
}
