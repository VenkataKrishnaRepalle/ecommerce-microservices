package com.pm.spring.ema.shoppingcart.model.repository;

import com.pm.spring.ema.shoppingcart.model.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, UUID> {
}
