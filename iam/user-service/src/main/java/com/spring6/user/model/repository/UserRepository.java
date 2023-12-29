package com.pm.spring.ema.user.model.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Long countById(UUID id);

    @Query("SELECT b FROM UserProfile b WHERE b.username LIKE %?1%")
    Page<com.pm.spring.ema.user.model.entity.User> findAll(String keyword, Pageable pageable);

    @Query("SELECT b FROM UserProfile b WHERE b.firstName LIKE %?1%")
    Page<com.pm.spring.ema.user.model.entity.User> findAllByFirstName(String searchKeyword, Pageable pageable);

    @Query("SELECT b FROM UserProfile b WHERE b.lastName LIKE %?1%")
    Page<com.pm.spring.ema.user.model.entity.User> findAllByLastName(String searchKeyword, Pageable pageable);

    @Query("SELECT b FROM UserProfile b WHERE b.email LIKE %?1%")
    Page<com.pm.spring.ema.user.model.entity.User> findAllByEmail(String searchKeyword, Pageable pageable);

    @Query("SELECT b FROM UserProfile b WHERE b.username LIKE %?1%")
    Page<com.pm.spring.ema.user.model.entity.User> findAllByUsername(String searchKeyword, Pageable pageable);

    Optional<com.pm.spring.ema.user.model.entity.User> findByUsername(String username);

    Optional<com.pm.spring.ema.user.model.entity.User> findByEmail(String email);
}
