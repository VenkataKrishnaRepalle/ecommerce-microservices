package com.spring.ema.shoppingcart.model.repository;

import com.spring.ema.shoppingcart.model.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,UUID> {
}
