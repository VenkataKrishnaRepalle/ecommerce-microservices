package com.spring6.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @Column(unique = true, length = 256, nullable = false)
    private String name;

    @Column(unique = true, length = 256, nullable = false, name = "alias")
    private String alias;

    @Column(length = 512, nullable = false, name = "short_description")
    private String shortDescription;

    @Column(length = 4096, nullable = false, name = "full_description")
    private String fullDescription;

    @Column(name = "in_stock")
    private Boolean inStock;

    @Column(nullable = false)
    private Float cost;

    private Float price;

    private Float discountPercent;

    private Float length;

    private Float width;

    private Float weight;

    private Float height;

    @Column(name = "main_image")
    private String mainImage;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "varchar(36)", nullable = false)
    private UUID categoryId;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "varchar(36)",nullable = false)
    private UUID brandId;

    private Boolean isEnabled;

    @CreationTimestamp
    @Column(name = "created_time", updatable = false)
    private Date createdTime;

    @UpdateTimestamp
    @Column(name = "updated_time")
    private Date updatedTime;
}