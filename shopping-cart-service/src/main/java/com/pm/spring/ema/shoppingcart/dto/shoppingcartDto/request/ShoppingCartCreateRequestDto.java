package com.pm.spring.ema.shoppingcart.dto.shoppingcartDto.request;

import com.pm.spring.ema.common.util.exception.ErrorCodes;
import com.pm.spring.ema.shoppingcart.enums.ShoppingCartStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;


import java.util.UUID;

@Data
@Builder
public class ShoppingCartCreateRequestDto {

    @NotNull(message = ErrorCodes.E1600)
    private UUID customerId;

    @NotNull(message = ErrorCodes.E1601)
    @Enumerated(EnumType.STRING)
    private ShoppingCartStatus status;
}
