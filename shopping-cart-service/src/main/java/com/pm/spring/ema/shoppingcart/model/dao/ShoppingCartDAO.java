package com.pm.spring.ema.shoppingcart.model.dao;

import com.pm.spring.ema.shoppingcart.model.entity.ShoppingCart;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShoppingCartDAO{
    List<ShoppingCart> findAll();

    ShoppingCart save(ShoppingCart shoppingCart);

    Optional<ShoppingCart> findById(UUID uuid);

    void deleteById(UUID uuid);
    long count();

    Optional<ShoppingCart> findByCustomerId(UUID customerId);
}
