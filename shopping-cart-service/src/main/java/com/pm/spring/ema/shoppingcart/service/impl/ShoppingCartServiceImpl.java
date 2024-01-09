package com.pm.spring.ema.shoppingcart.service.impl;

import com.pm.spring.ema.common.util.exception.ErrorCodes;
import com.pm.spring.ema.common.util.exception.ErrorMessage;
import com.pm.spring.ema.shoppingcart.dto.shoppingcartDto.request.ShoppingCartCreateRequestDto;
import com.pm.spring.ema.shoppingcart.dto.shoppingcartDto.response.ShoppingCartCreateResponseDto;
import com.pm.spring.ema.shoppingcart.exception.shoppingCartException.ShoppingCartAlreadyExistException;
import com.pm.spring.ema.shoppingcart.exception.shoppingCartException.ShoppingCartNotFoundException;
import com.pm.spring.ema.shoppingcart.mapper.ShoppingCartMapper;
import com.pm.spring.ema.shoppingcart.model.entity.CartItems;
import com.pm.spring.ema.shoppingcart.model.entity.ShoppingCart;
import com.pm.spring.ema.shoppingcart.model.repository.CartItemsRepository;
import com.pm.spring.ema.shoppingcart.model.repository.ShoppingCartRepository;
import com.pm.spring.ema.shoppingcart.service.ShoppingCartService;
import com.pm.spring.ema.shoppingcart.utils.TraceIdHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemsRepository cartItemsRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    public ShoppingCartCreateResponseDto createShoppingCart(ShoppingCartCreateRequestDto shoppingCartCreateRequestDto) throws ShoppingCartAlreadyExistException {

        log.debug("ShoppingCartService:createShoppingCart execution started. traceId: {}, shoppingCartCreateRequestDto: {} ", TraceIdHolder.getTraceId(), shoppingCartCreateRequestDto);

        if ((isShoppingCartExistById(shoppingCartCreateRequestDto.getCustomerId()))) {
            log.error("ShoppingCartService:createShoppingCart traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E1550, shoppingCartCreateRequestDto.getCustomerId().toString()));
            throw new ShoppingCartAlreadyExistException(ErrorCodes.E1550, shoppingCartCreateRequestDto.getCustomerId().toString());

        }

        ShoppingCart shoppingCart = shoppingCartMapper.convertToShoppingCart(shoppingCartCreateRequestDto);
        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);
        ShoppingCartCreateResponseDto shoppingCartCreateResponseDto = shoppingCartMapper.convertToShoppingCartCreateResponseDto(savedShoppingCart);

        log.debug("ShoppingCartService:createShoppingCart execution ended. traceId: {}, response: {}", TraceIdHolder.getTraceId(), shoppingCartCreateResponseDto);

        return shoppingCartCreateResponseDto;
    }

    @Override
    public List<ShoppingCartCreateResponseDto> getAllShoppingCarts() {

        log.debug("ShoppingCartService:getAllShoppingCarts execution started. traceId: {}", TraceIdHolder.getTraceId());

        List<ShoppingCartCreateResponseDto> shoppingCartCreateResponseDtoList = shoppingCartRepository.findAll()
                .stream()
                .map(shoppingCartMapper::convertToShoppingCartCreateResponseDto)
                .toList();
        log.debug("ShoppingCartService:getAllShoppingCarts execution ended. traceId: {}, response {} ", TraceIdHolder.getTraceId(), shoppingCartCreateResponseDtoList);


        return shoppingCartCreateResponseDtoList;
    }

    @Override
    public ShoppingCartCreateResponseDto getShoppingCartById(UUID uuid) throws ShoppingCartNotFoundException {

        log.debug("ShoppingCartService:getShoppingCartById execution started. traceId: {}, uuid : {}", TraceIdHolder.getTraceId(), uuid);

        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findById(uuid);

        if (shoppingCart.isPresent()) {

            ShoppingCartCreateResponseDto shoppingCartCreateResponseDto = shoppingCartMapper.convertToShoppingCartCreateResponseDto(shoppingCart.get());

            log.debug("ShoppingCartService:getShoppingCartById execution ended. traceId: {}, response: {}", TraceIdHolder.getTraceId(), shoppingCartCreateResponseDto);

            return shoppingCartCreateResponseDto;
        } else {

            log.error("ShoppingCartService:getShoppingCartById execution ended. traceId: {}", TraceIdHolder.getTraceId());

            throw new ShoppingCartNotFoundException(ErrorCodes.E1551, uuid.toString());


        }


    }

    @Override
    public ShoppingCartCreateResponseDto getShoppingCartByCustomerId(UUID uuid) throws ShoppingCartNotFoundException {

        log.debug("ShoppingCartService:getShoppingCartByUserId execution started. traceId: {}, uuid : {}", TraceIdHolder.getTraceId(), uuid);

        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findByCustomerId(uuid);

        if (shoppingCart.isPresent()) {

            ShoppingCartCreateResponseDto shoppingCartCreateResponseDto = shoppingCartMapper.convertToShoppingCartCreateResponseDto(shoppingCart.get());

            log.debug("ShoppingCartService:getShoppingCartByUserId execution ended. traceId: {}, response: {}", TraceIdHolder.getTraceId(), shoppingCartCreateResponseDto);

            return shoppingCartCreateResponseDto;

        } else {

            log.error("ShoppingCartService:getShoppingCartByUserId execution ended. traceId: {}", TraceIdHolder.getTraceId());

            throw new ShoppingCartNotFoundException(ErrorCodes.E1552, uuid.toString());


        }

    }

    @Override
    public void deleteShoppingCartById(UUID uuid) throws ShoppingCartNotFoundException {
        log.debug("ShoppingCartService:deleteShoppingCartById execution started. traceId: {}, uuid : {}", TraceIdHolder.getTraceId(), uuid);

        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findById(uuid);

        if (optionalShoppingCart.isPresent()) {
            ShoppingCart shoppingCart = optionalShoppingCart.get();
            for (CartItems cartItems : cartItemsRepository.findByShoppingCart(shoppingCart)){
                cartItemsRepository.delete(cartItems);
            }

            shoppingCartRepository.deleteById(shoppingCart.getId());

            log.debug("ShoppingCartService:deleteShoppingCartById execution ended.");

        } else {
            log.error("ShoppingCartService:deleteShoppingCartById execution ended. traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E1511, uuid.toString()));

            throw new ShoppingCartNotFoundException(ErrorCodes.E1513, uuid.toString());
        }


    }

    @Override
    public Boolean isShoppingCartExistById(UUID uuid) {

        log.debug("ShoppingCartService:isShoppingCartExistById execution started. traceId: {}", TraceIdHolder.getTraceId());

        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findByCustomerId(uuid);
        if (optionalShoppingCart.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
