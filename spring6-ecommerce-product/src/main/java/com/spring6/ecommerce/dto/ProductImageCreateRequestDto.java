package com.spring6.ecommerce.dto;

import jakarta.persistence.*;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ProductImageCreateRequestDto {


    @NotNull
    private String name;

    @NotNull
    private UUID productId;

}
