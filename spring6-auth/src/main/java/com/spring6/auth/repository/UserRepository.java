package com.spring6.auth.repository;

import java.util.Optional;
import java.util.UUID;

import com.spring6.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findByEmail(String email);
  User findByUsername(String username);


}
