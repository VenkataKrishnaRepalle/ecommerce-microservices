package com.pm.spring.ema.auth.service.common.model.repository;

import com.pm.spring.ema.auth.service.common.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
}