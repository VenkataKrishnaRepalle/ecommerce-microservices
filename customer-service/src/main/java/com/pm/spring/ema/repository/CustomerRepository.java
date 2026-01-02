package com.pm.spring.ema.repository;

import com.pm.spring.ema.modal.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    @Query("SELECT c FROM Customer c WHERE c.email = :email")
    Customer findByEmail(@Param("email") String email);

}
