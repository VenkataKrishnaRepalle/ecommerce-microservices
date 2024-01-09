package com.pm.spring.ema.shoppingcart.model.repository;

import com.pm.spring.ema.shoppingcart.model.entity.CartItems;
import com.pm.spring.ema.shoppingcart.model.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, UUID> {

    List<CartItems> findByShoppingCart(ShoppingCart shoppingCart);

    @Query("SELECT ci FROM CartItems ci WHERE ci.productId = :productId AND ci.shoppingCart.id = :shoppingCartId")
    Optional<CartItems> findByProductIdAndShoppingCart(UUID productId, UUID shoppingCartId);

    @Query("SELECT ci FROM CartItems ci WHERE ci.shoppingCart.id = :shoppingCartId")
    List<CartItems> findByShoppingCartId(UUID shoppingCartId);
}
