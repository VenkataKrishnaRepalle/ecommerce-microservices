package com.pm.spring.ema.shoppingcart.service;

import com.pm.spring.ema.shoppingcart.dto.cartItemsDto.request.CartItemsCreateRequestDto;
import com.pm.spring.ema.shoppingcart.dto.cartItemsDto.request.CartItemsUpdateRequestDto;
import com.pm.spring.ema.shoppingcart.dto.cartItemsDto.response.CartItemsResponseDto;
import com.pm.spring.ema.shoppingcart.dto.cartItemsDto.response.CartItemsUpdateResponseDto;

import java.util.List;
import java.util.UUID;

public interface CartItemsService {

    List<CartItemsResponseDto> getAllCartItems();

    CartItemsResponseDto getCartItemsById(UUID uuid);

    CartItemsResponseDto createCartItems(CartItemsCreateRequestDto cartItemsCreateRequestDto);


    List<CartItemsResponseDto> getCartItemsByShoppingCartId(UUID ShoppingCartId);

    CartItemsUpdateResponseDto updateCartItems (UUID cartItemId, CartItemsUpdateRequestDto subCategoryRequestDto);


    void deleteCartItemsById(UUID uuid);




}
