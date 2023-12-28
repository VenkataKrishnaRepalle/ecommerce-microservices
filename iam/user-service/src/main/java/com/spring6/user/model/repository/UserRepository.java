package com.pm.spring.ema.user.model.repository;


import com.pm.spring.ema.user.model.entity.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserProfile, UUID> {

    Long countById(UUID id);

    @Query("SELECT b FROM UserProfile b WHERE b.username LIKE %?1%")
    Page<UserProfile> findAll(String keyword, Pageable pageable);

    @Query("SELECT b FROM UserProfile b WHERE b.firstName LIKE %?1%")
    Page<UserProfile> findAllByFirstName(String searchKeyword, Pageable pageable);

    @Query("SELECT b FROM UserProfile b WHERE b.lastName LIKE %?1%")
    Page<UserProfile> findAllByLastName(String searchKeyword, Pageable pageable);

    @Query("SELECT b FROM UserProfile b WHERE b.email LIKE %?1%")
    Page<UserProfile> findAllByEmail(String searchKeyword, Pageable pageable);

    @Query("SELECT b FROM UserProfile b WHERE b.username LIKE %?1%")
    Page<UserProfile> findAllByUsername(String searchKeyword, Pageable pageable);

    Optional<UserProfile> findByUsername(String username);

    Optional<UserProfile> findByEmail(String email);
}
