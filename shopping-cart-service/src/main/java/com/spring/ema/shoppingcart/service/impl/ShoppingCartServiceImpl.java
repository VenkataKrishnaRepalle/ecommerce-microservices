package com.spring.ema.shoppingcart.service.impl;

import com.pm.spring.ema.common.util.exception.ErrorCodes;
import com.pm.spring.ema.common.util.exception.ErrorMessage;
import com.spring.ema.shoppingcart.dto.shoppingcartDto.request.ShoppingCartCreateRequestDto;
import com.spring.ema.shoppingcart.dto.shoppingcartDto.response.ShoppingCartCreateResponseDto;
import com.spring.ema.shoppingcart.exception.shoppingCartException.ShoppingCartAlreadyExistException;
import com.spring.ema.shoppingcart.mapper.ShoppingCartMapper;
import com.spring.ema.shoppingcart.model.dao.shoppingcartDAO.ShoppingCartDAO;
import com.spring.ema.shoppingcart.model.entity.ShoppingCart;
import com.spring.ema.shoppingcart.service.ShoppingCartService;
import com.spring.ema.shoppingcart.utils.TraceIdHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartDAO shoppingCartDAO;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    public ShoppingCartCreateResponseDto createShoppingCart(ShoppingCartCreateRequestDto shoppingCartCreateRequestDto) throws ShoppingCartAlreadyExistException {
        log.info("ShoppingCartService:createShoppingCart execution started.");
        log.debug("ShoppingCartService:createShoppingCart traceId: {}, shoppingCartCreateRequestDto: {} ", TraceIdHolder.getTraceId(), shoppingCartCreateRequestDto);

        if ((isShoppingCartExistById(shoppingCartCreateRequestDto.getCustomerId()))) {
            log.error("ShoppingCartService:createShoppingCart traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E1550, shoppingCartCreateRequestDto.getCustomerId().toString()));
            throw new ShoppingCartAlreadyExistException(ErrorCodes.E1550, shoppingCartCreateRequestDto.getCustomerId().toString());

        }

        ShoppingCart shoppingCart = shoppingCartMapper.convertToShoppingCart(shoppingCartCreateRequestDto);
        ShoppingCart savedShoppingCart = shoppingCartDAO.save(shoppingCart);
        ShoppingCartCreateResponseDto shoppingCartCreateResponseDto = shoppingCartMapper.convertToShoppingCartCreateResponseDto(savedShoppingCart);

        log.debug("ShoppingCartService:createShoppingCart traceId: {}, response: {}", TraceIdHolder.getTraceId(), shoppingCartCreateResponseDto);
        log.info("ShoppingCartService:createShoppingCart execution ended.");
        return shoppingCartCreateResponseDto;
    }

    @Override
    public List<ShoppingCartCreateResponseDto> getAllShoppingCarts() {
        log.info("ShoppingCartService:getAllShoppingCarts execution started.");
        log.debug("ShoppingCartService:getAllShoppingCarts traceId: {}", TraceIdHolder.getTraceId());

        List<ShoppingCartCreateResponseDto> shoppingCartCreateResponseDtoList = shoppingCartDAO.findAll()
                .stream()
                .map(shoppingCartMapper::convertToShoppingCartCreateResponseDto)
                .toList();
        log.debug("ShoppingCartService:getAllShoppingCarts traceId: {}, response {} ", TraceIdHolder.getTraceId(), shoppingCartCreateResponseDtoList);
        log.info("ShoppingCartService:getAllShoppingCarts execution ended.");


        return shoppingCartCreateResponseDtoList;
    }

    @Override
    public ShoppingCartCreateResponseDto getShoppingCartById(UUID uuid) {
        return null;
    }

    @Override
    public ShoppingCartCreateResponseDto getShoppingCartByUserId(UUID uuid) {
        return null;
    }

    @Override
    public void deleteShoppingCartById(UUID uuid) {

    }

    @Override
    public Boolean isShoppingCartExistById(UUID uuid) {

        log.info("ShoppingCartService:isShoppingCartExistById execution started. traceId: {}", TraceIdHolder.getTraceId());

        Optional<ShoppingCart> optionalShoppingCart = shoppingCartDAO.findById(uuid);
        if (optionalShoppingCart.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
