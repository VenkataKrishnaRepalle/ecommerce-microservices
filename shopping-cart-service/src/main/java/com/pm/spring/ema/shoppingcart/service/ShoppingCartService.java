package com.pm.spring.ema.shoppingcart.service;

import com.pm.spring.ema.shoppingcart.dto.shoppingcartDto.request.ShoppingCartCreateRequestDto;
import com.pm.spring.ema.shoppingcart.dto.shoppingcartDto.response.ShoppingCartCreateResponseDto;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;
@Service
public interface ShoppingCartService {
    ShoppingCartCreateResponseDto createShoppingCart(ShoppingCartCreateRequestDto shoppingCartCreateRequestDto);

    List<ShoppingCartCreateResponseDto> getAllShoppingCarts();

    ShoppingCartCreateResponseDto getShoppingCartById(UUID uuid);

    ShoppingCartCreateResponseDto getShoppingCartByUserId(UUID customerId);

    void  deleteShoppingCartById(UUID uuid);

    Boolean isShoppingCartExistById(UUID uuid);


}
