package com.pm.spring.ema.shoppingcart.dto.cartItemsDto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@Builder
public class CartItemsCreateRequestDto {

    @NotNull
    private UUID productId;

    @NotNull
    private int quantity;

    @NotNull
    private UUID shoppingCartId;


}
