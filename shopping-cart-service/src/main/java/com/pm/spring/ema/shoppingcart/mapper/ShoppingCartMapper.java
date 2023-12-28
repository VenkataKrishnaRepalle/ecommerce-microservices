package com.pm.spring.ema.shoppingcart.mapper;

import com.pm.spring.ema.shoppingcart.dto.shoppingcartDto.request.ShoppingCartCreateRequestDto;
import com.pm.spring.ema.shoppingcart.dto.shoppingcartDto.response.ShoppingCartCreateResponseDto;
import com.pm.spring.ema.shoppingcart.model.entity.ShoppingCart;
import org.mapstruct.Mapper;

@Mapper
public interface ShoppingCartMapper {

    ShoppingCart convertToShoppingCart(ShoppingCartCreateRequestDto shoppingCartCreateRequestDto);
    ShoppingCartCreateResponseDto convertToShoppingCartCreateResponseDto(ShoppingCart shoppingCart);


}
