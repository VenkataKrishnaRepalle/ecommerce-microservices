package com.pm.spring.ema.shoppingcart.mapper;

import com.pm.spring.ema.shoppingcart.dto.cartItemsDto.request.CartItemsCreateRequestDto;
import com.pm.spring.ema.shoppingcart.dto.cartItemsDto.request.CartItemsUpdateRequestDto;
import com.pm.spring.ema.shoppingcart.dto.cartItemsDto.response.CartItemsResponseDto;
import com.pm.spring.ema.shoppingcart.dto.cartItemsDto.response.CartItemsUpdateResponseDto;
import com.pm.spring.ema.shoppingcart.model.entity.CartItems;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CartItemsMapper {
    CartItems convertCartItemsCreateRequestDtoToCartItems(CartItemsCreateRequestDto cartItemsCreateRequestDto);

    @Mapping(source = "shoppingCart.id", target = "shoppingCartId")
    CartItemsResponseDto convertToCartItemsResponseDto(CartItems cartItems);

    CartItems convertCartItemsUpdateRequestDtoToCartItems(CartItemsUpdateRequestDto cartItemsUpdateRequestDto);
    CartItemsUpdateResponseDto convertToCartItemsUpdateResponseDto(CartItems cartItems);


}
