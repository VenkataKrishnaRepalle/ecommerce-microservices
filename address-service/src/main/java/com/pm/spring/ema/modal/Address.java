package com.pm.spring.ema.modal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.util.Date;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "address")
public class Address {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @JdbcTypeCode(SqlTypes.CHAR)
  @Column(columnDefinition = "varchar(36)", updatable = false, nullable = false)
  private UUID id;

  @JdbcTypeCode(SqlTypes.CHAR)
  @Column(columnDefinition = "varchar(36)", updatable = false, nullable = false)
  private UUID userUuid;

  @Column(name = "country", nullable = false)
  private String country;

  @Column(name = "full_name", nullable = false)
  private String fullName;

  @Column(name = "mobile_number", nullable = false)
  @Size(min = 10, max = 10)
  private String mobileNumber;

  @Column(name = "pincode", nullable = false)
  @Size(min = 6, max = 6)
  private String pincode;

  @Column(name = "house_number", nullable = false)
  @Size(max = 20)
  private String houseNumber;

  @Column(name = "area", nullable = false)
  @Size(max = 30)
  private String area;

  @Column(name = "landmark", nullable = false)
  @Size(max = 30)
  private String landmark;

  @Column(name = "city", nullable = false)
  private String city;

  @Column(name = "state", nullable = false)
  private String state;

  @Column(name = "default_address", nullable = false)
  private Boolean defaultAddress;

  @CreationTimestamp
  @Column(name = "created_time", nullable = false, updatable = false)
  private Date createdTime;

  @UpdateTimestamp
  @Column(name = "updated_time", nullable = false)
  private Date updatedTime;
}
