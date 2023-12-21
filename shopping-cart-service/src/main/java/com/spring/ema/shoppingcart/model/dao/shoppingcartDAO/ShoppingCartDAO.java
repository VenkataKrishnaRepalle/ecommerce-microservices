package com.spring.ema.shoppingcart.model.dao.shoppingcartDAO;

import com.spring.ema.shoppingcart.model.entity.ShoppingCart;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShoppingCartDAO{
    List<ShoppingCart> findAll();

    ShoppingCart save(ShoppingCart shoppingCart);

    Optional<ShoppingCart> findById(UUID uuid);

    void deleteById(UUID uuid);
    long count();
}
