package com.spring.ema.shoppingcart.dto.shoppingcartDto.request;

import com.spring.ema.shoppingcart.enums.ShoppingCartStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;


import java.util.UUID;

@Data
@Builder
public class ShoppingCartCreateRequestDto {

    @NotNull
    private UUID customerId;

    @Enumerated(EnumType.STRING)
    private ShoppingCartStatus status;
}
