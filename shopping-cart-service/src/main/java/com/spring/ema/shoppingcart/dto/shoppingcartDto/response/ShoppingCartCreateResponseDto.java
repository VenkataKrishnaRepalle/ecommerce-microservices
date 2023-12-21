package com.spring.ema.shoppingcart.dto.shoppingcartDto.response;

import com.spring.ema.shoppingcart.enums.ShoppingCartStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
