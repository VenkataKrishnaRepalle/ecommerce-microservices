package com.pm.spring.ema.shoppingcart.dto.cartItemsDto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CartItemsResponseDto {

    @NotNull
    private UUID id;

    @NotNull
    private UUID productId;

    @NotNull
    private int quantity;

    @NotNull
    private UUID shoppingCartId;


}
