package com.pm.spring.ema.shoppingcart.model.dao.impl;

import com.pm.spring.ema.shoppingcart.model.dao.ShoppingCartDAO;
import com.pm.spring.ema.shoppingcart.model.entity.ShoppingCart;
import com.pm.spring.ema.shoppingcart.model.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class ShoppingCartDaoImpl implements ShoppingCartDAO {
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public ShoppingCartDaoImpl(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

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

    @Override
    public Optional<ShoppingCart> findByCustomerId(UUID customerId) {
        return shoppingCartRepository.findByCustomerId(customerId);
    }
}
