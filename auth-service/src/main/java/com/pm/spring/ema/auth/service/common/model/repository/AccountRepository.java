package com.pm.spring.ema.auth.service.common.model.repository;

import com.pm.spring.ema.auth.service.common.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findByUsername(String username);

    Optional<Account> findByEmail(String email);

//    @Query("UPDATE Customer c SET c.password = :password WHERE c.email = :email")
//    @Modifying
//    void forgotPassword(@Param("email") String email, @Param("password") String password);

}
