package com.spring6.model.repository;

import com.spring6.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    @Query("SELECT c FROM Customer c WHERE c.email = :email")
    Customer findByEmail(@Param("email") String email);

    @Query("UPDATE Customer c SET c.oneTimePassword = :OTP, c.otpRequestedTime = :OTPRequestedTime WHERE c.id = :id")
    @Modifying
    void OTPUpdate(@Param("OTP") String OTP, @Param("OTPRequestedTime")Date OTPRequestedTime, @Param("id") UUID id);


}
