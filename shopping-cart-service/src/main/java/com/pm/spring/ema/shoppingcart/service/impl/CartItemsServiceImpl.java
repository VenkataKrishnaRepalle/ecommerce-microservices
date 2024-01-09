package com.pm.spring.ema.shoppingcart.service.impl;

import com.pm.spring.ema.common.util.exception.ErrorCodes;
import com.pm.spring.ema.common.util.exception.ErrorMessage;
import com.pm.spring.ema.shoppingcart.dto.cartItemsDto.request.CartItemsCreateRequestDto;
import com.pm.spring.ema.shoppingcart.dto.cartItemsDto.request.CartItemsUpdateRequestDto;
import com.pm.spring.ema.shoppingcart.dto.cartItemsDto.response.CartItemsResponseDto;
import com.pm.spring.ema.shoppingcart.dto.cartItemsDto.response.CartItemsUpdateResponseDto;
import com.pm.spring.ema.shoppingcart.exception.cartItemsException.CartItemsNotFoundException;
import com.pm.spring.ema.shoppingcart.exception.shoppingCartException.ShoppingCartNotFoundException;
import com.pm.spring.ema.shoppingcart.mapper.CartItemsMapper;
import com.pm.spring.ema.shoppingcart.model.entity.CartItems;
import com.pm.spring.ema.shoppingcart.model.entity.ShoppingCart;
import com.pm.spring.ema.shoppingcart.model.repository.CartItemsRepository;
import com.pm.spring.ema.shoppingcart.model.repository.ShoppingCartRepository;
import com.pm.spring.ema.shoppingcart.service.CartItemsService;
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
public class CartItemsServiceImpl implements CartItemsService {
    private final CartItemsRepository cartItemsRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemsMapper cartItemsMapper;

    @Override
    public CartItemsResponseDto createCartItems(CartItemsCreateRequestDto cartItemsCreateRequestDto) {

        log.debug("CartItemsService:createCartItems EXECUTION STARTED. traceId: {} , cartItemsCreateRequestDto: {}", TraceIdHolder.getTraceId(), cartItemsCreateRequestDto);

        UUID productId = cartItemsCreateRequestDto.getProductId();

        UUID shoppingCartId = cartItemsCreateRequestDto.getShoppingCartId();

        Optional<CartItems> existingCartItemOptional = cartItemsRepository.findByProductIdAndShoppingCart(productId, shoppingCartId);

        if (existingCartItemOptional.isPresent()) {

            CartItems existingCartItems = existingCartItemOptional.get();

            CartItemsResponseDto cartItemsResponseDto = cartItemsMapper.convertToCartItemsResponseDto(existingCartItems);

            log.debug("CartItemsService:createCartItems EXECUTION ENDED. traceId: {}, response: {}", TraceIdHolder.getTraceId(), cartItemsResponseDto);

            return cartItemsResponseDto;
        } else {


            CartItems cartItems = cartItemsMapper.convertCartItemsCreateRequestDtoToCartItems(cartItemsCreateRequestDto);

            UUID existingShoppingCartId = cartItemsCreateRequestDto.getShoppingCartId();

            ShoppingCart shoppingCart = shoppingCartRepository.findById(existingShoppingCartId).orElseThrow(() -> new ShoppingCartNotFoundException(ErrorCodes.E1609, existingShoppingCartId.toString()));
            cartItems.setShoppingCart(shoppingCart);
            CartItems savedCartItems = cartItemsRepository.save(cartItems);
            CartItemsResponseDto cartItemsResponseDto = cartItemsMapper.convertToCartItemsResponseDto(savedCartItems);
            cartItemsResponseDto.setShoppingCartId(existingShoppingCartId);

            log.debug("CartItemsService:createCartItems EXECUTION ENDED. traceId: {}, response: {}", TraceIdHolder.getTraceId(), cartItemsResponseDto);

            return cartItemsResponseDto;
        }


    }

    @Override
    public List<CartItemsResponseDto> getAllCartItems() {

        log.debug("CartItemsService:getAllCartItems EXECUTION STARTED.  traceId: {}", TraceIdHolder.getTraceId());
        List<CartItemsResponseDto> cartItemsResponseDtoList = cartItemsRepository.findAll()
                .stream()
                .map(cartItemsMapper::convertToCartItemsResponseDto)
                .toList();
        log.debug("CartItemsService:getAllCartItems EXECUTION ENDED. traceId: {}, response {} ", TraceIdHolder.getTraceId(), cartItemsResponseDtoList);

        return cartItemsResponseDtoList;
    }

    @Override
    public CartItemsResponseDto getCartItemsById(UUID uuid) throws CartItemsNotFoundException {
        log.debug("CartItemsService:getCartItemsById EXECUTION STARTED.  traceId: {}, id: {}", TraceIdHolder.getTraceId(), uuid);

        Optional<CartItems> optionalCartItems = cartItemsRepository.findById(uuid);
        if (optionalCartItems.isEmpty()) {
            log.error("CartItemsService:getCartItemsById EXECUTION ENDED. traceId: {}", TraceIdHolder.getTraceId());
            throw new CartItemsNotFoundException(ErrorCodes.E1509, uuid.toString());
        }

        CartItemsResponseDto cartItemsResponseDto = cartItemsMapper.convertToCartItemsResponseDto(optionalCartItems.get());
        log.debug("CartItemsService:getCartItemsById EXECUTION ENDED. traceId: {}, response: {}", TraceIdHolder.getTraceId(), cartItemsResponseDto);

        return cartItemsResponseDto;
    }

    @Override
    public List<CartItemsResponseDto> getCartItemsByShoppingCartId(UUID shoppingCartId) throws CartItemsNotFoundException {

//        List<CartItems> optionalCartItems = cartItemsRepository.findByShoppingCartId(ShoppingCartId);
//
//        if (optionalCartItems.isEmpty()) {
//            log.error("CartItemsService:getCartItemsByShoppingCartId EXECUTION ENDED. traceId: {}, errorMessage: Cart Items not found", TraceIdHolder.getTraceId());
//            throw new CartItemsNotFoundException(ErrorCodes.E1606, ShoppingCartId.toString());
//        }
//
//        List<CartItemsResponseDto> cartItemsResponseDtoList = optionalCartItems.stream()
//                .map(cartItemsMapper::convertToCartItemsResponseDto).toList();
//
//        log.debug("CartItemsService:getCartItemsByShoppingCartId EXECUTION ENDED. traceId: {}, response: {}", TraceIdHolder.getTraceId(), cartItemsResponseDtoList);
//
//        return cartItemsResponseDtoList;


        return cartItemsRepository.findByShoppingCart(ShoppingCart.builder().id(shoppingCartId).build())
                .stream()
                .map(cartItemsMapper::convertToCartItemsResponseDto)
                .toList();

    }

    @Override
    public CartItemsUpdateResponseDto updateCartItems(UUID cartItemId, CartItemsUpdateRequestDto cartItemsUpdateRequestDto) throws CartItemsNotFoundException {

        log.debug("CartItemsService:updateCartItems execution started userId:{},cartItemId:{}, cartItemsUpdateRequestDto: {}", cartItemId, cartItemsUpdateRequestDto);

        Optional<CartItems> optionalCartItems = cartItemsRepository.findById(cartItemId);

        if (optionalCartItems.isPresent()) {

            CartItems updateCartItem = cartItemsMapper.convertCartItemsUpdateRequestDtoToCartItems(cartItemsUpdateRequestDto);

            updateCartItem.setQuantity(cartItemsUpdateRequestDto.getQuantity());

            cartItemsRepository.save(updateCartItem);

            CartItemsUpdateResponseDto cartItemsUpdateResponseDto = cartItemsMapper.convertToCartItemsUpdateResponseDto(updateCartItem);

            log.debug("CartItemsService:updateCartItems EXECUTION ENDED. traceId: {}, response: {}", TraceIdHolder.getTraceId(), cartItemsUpdateResponseDto);

            return cartItemsUpdateResponseDto;

        } else {
            log.error("CartItemsService:updateCartItems errorCode:{E1607},errorMessage:{},cartItemId:{}", ErrorCodes.E1607, cartItemId);
            throw new CartItemsNotFoundException(ErrorCodes.E1607, cartItemId.toString());

        }

    }

    @Override
    public void deleteCartItemsById(UUID uuid) throws CartItemsNotFoundException {

        log.debug("CartItemsService:deleteCartItemsById execution started. traceId: {}, uuid : {}", TraceIdHolder.getTraceId(), uuid);

        Optional<CartItems> cartItems = cartItemsRepository.findById(uuid);

        if (cartItems.isPresent()) {

            cartItemsRepository.deleteById(cartItems.get().getId());

            log.debug("CartItemsService:deleteCartItemsById execution ended.");

        } else {
            log.error("CartItemsService:deleteCartItemsById execution ended. traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E1608, uuid.toString()));

            throw new CartItemsNotFoundException(ErrorCodes.E1608, uuid.toString());
        }


    }

}
