package com.spring.ema.shoppingcart.model.dao.shoppingcartDAO;

import com.spring.ema.shoppingcart.model.entity.ShoppingCart;
import com.spring.ema.shoppingcart.model.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class ShoppingCartDaoImpl implements ShoppingCartDAO{
    ShoppingCartRepository shoppingCartRepository;

    @Override
    public List<ShoppingCart> findAll() {
        return shoppingCartRepository.findAll();
    }

    @Override
    public ShoppingCart save(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public Optional<ShoppingCart> findById(UUID uuid) {
        return shoppingCartRepository.findById(uuid);
    }

    @Override
    public void deleteById(UUID uuid) {

        shoppingCartRepository.deleteById(uuid);
    }

    @Override
    public long count() {
        return shoppingCartRepository.count();
    }
}
