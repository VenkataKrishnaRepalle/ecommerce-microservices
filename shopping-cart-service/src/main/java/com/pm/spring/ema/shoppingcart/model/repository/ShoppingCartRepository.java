package com.pm.spring.ema.shoppingcart.model.repository;

import com.pm.spring.ema.shoppingcart.model.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,UUID> {

    Optional<ShoppingCart> findByCustomerId(UUID customerId);

}
