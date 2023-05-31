package com.spring6.ecommerce.entity;

import jakarta.persistence.*;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;
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
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "varchar(36)",
            updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public ProductImage(String name, Product product) {
        this.name = name;
        this.product = product;
    }
}
