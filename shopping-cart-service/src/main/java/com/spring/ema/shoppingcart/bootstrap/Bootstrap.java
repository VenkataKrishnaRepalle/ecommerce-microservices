package com.spring.ema.shoppingcart.bootstrap;

import com.spring.ema.shoppingcart.enums.ShoppingCartStatus;
import com.spring.ema.shoppingcart.model.dao.shoppingcartDAO.ShoppingCartDAO;
import com.spring.ema.shoppingcart.model.entity.ShoppingCart;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class Bootstrap implements CommandLineRunner {
    private final ShoppingCartDAO shoppingCartDAO;

    @Override
    public void run(String... args) throws Exception {
        loadCategoryAndSubCategoryData();
    }

    private void loadCategoryAndSubCategoryData() {
        if (shoppingCartDAO.count() == 0) {

            shoppingCartDAO.save(ShoppingCart.builder()
                    .id(UUID.randomUUID())
                    .status(ShoppingCartStatus.ACTIVE)
                    .customerId(UUID.randomUUID())
                    .build());

        }


    }

}
