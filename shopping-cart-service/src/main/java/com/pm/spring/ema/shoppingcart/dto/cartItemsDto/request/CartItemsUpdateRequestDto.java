package com.pm.spring.ema.shoppingcart.dto.cartItemsDto.request;

import com.pm.spring.ema.common.util.exception.ErrorCodes;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CartItemsUpdateRequestDto {

    @NotNull(message = ErrorCodes.E1604)
    private int quantity;


}
