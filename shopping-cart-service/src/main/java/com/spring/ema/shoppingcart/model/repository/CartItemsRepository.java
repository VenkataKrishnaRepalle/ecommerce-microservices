package com.spring.ema.shoppingcart.model.repository;

import com.spring.ema.shoppingcart.model.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartItemsRepository extends JpaRepository<CartItems, UUID> {
}
