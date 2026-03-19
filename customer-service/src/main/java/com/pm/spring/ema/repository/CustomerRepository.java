package com.pm.spring.ema.repository;

import com.pm.spring.ema.modal.Customer;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

  @Query("SELECT c FROM Customer c WHERE c.email = :email")
  Customer findByEmail(@Param("email") String email);
}
