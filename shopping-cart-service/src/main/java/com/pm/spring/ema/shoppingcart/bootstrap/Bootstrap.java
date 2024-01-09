package com.pm.spring.ema.shoppingcart.bootstrap;

import com.pm.spring.ema.shoppingcart.enums.ShoppingCartStatus;
import com.pm.spring.ema.shoppingcart.model.entity.CartItems;
import com.pm.spring.ema.shoppingcart.model.entity.ShoppingCart;
import com.pm.spring.ema.shoppingcart.model.repository.CartItemsRepository;
import com.pm.spring.ema.shoppingcart.model.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class Bootstrap implements CommandLineRunner {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemsRepository cartItemsRepository;

    @Override
    public void run(String... args) throws Exception {
        loadCategoryAndSubCategoryData();
    }

    private void loadCategoryAndSubCategoryData() {
        if (shoppingCartRepository.count() == 0) {

            ShoppingCart shoppingCartId1 = shoppingCartRepository.save(ShoppingCart.builder()
                    .id(UUID.randomUUID())
                    .status(ShoppingCartStatus.ACTIVE)
                    .customerId(UUID.randomUUID())
                    .build());
            ShoppingCart shoppingCartId2 = shoppingCartRepository.save(ShoppingCart.builder()
                    .id(UUID.randomUUID())
                    .status(ShoppingCartStatus.ACTIVE)
                    .customerId(UUID.randomUUID())
                    .build());

            cartItemsRepository.save(CartItems.builder()
                    .shoppingCart(shoppingCartId1)
                    .id(UUID.randomUUID())
                    .productId(UUID.randomUUID())
                    .quantity(2)
                    .build());
            cartItemsRepository.save(CartItems.builder()
                    .shoppingCart(shoppingCartId1)
                    .id(UUID.randomUUID())
                    .productId(UUID.randomUUID())
                    .quantity(3)
                    .build());
            cartItemsRepository.save(CartItems.builder()
                    .shoppingCart(shoppingCartId2)
                    .id(UUID.randomUUID())
                    .productId(UUID.randomUUID())
                    .quantity(4)
                    .build());
            cartItemsRepository.save(CartItems.builder()
                    .shoppingCart(shoppingCartId2)
                    .id(UUID.randomUUID())
                    .productId(UUID.randomUUID())
                    .quantity(1)
                    .build());


        }


    }

}
