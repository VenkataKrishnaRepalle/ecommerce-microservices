package com.spring6.ecommerce.dto;

import jakarta.persistence.*;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


@Data
public class ProductImageDto {

    private UUID id;

    @Column(nullable = false)
    private String name;

    private ProductCreateRequestDto product;

    public ProductImageDto(String name, ProductCreateRequestDto product) {
        this.name = name;
        this.product = product;
    }

}
