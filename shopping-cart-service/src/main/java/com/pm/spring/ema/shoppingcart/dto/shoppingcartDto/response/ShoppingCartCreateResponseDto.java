package com.pm.spring.ema.shoppingcart.dto.shoppingcartDto.response;

import com.pm.spring.ema.shoppingcart.enums.ShoppingCartStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ShoppingCartCreateResponseDto {
    @NotNull
    private UUID id;

    @NotNull
    private UUID customerId;

    @NotNull
    private ShoppingCartStatus status;

}
