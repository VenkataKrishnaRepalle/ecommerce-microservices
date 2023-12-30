package com.pm.spring.ema.model.dao;

import com.pm.spring.ema.model.entity.Customer;

import java.util.Date;
import java.util.UUID;

public interface CustomerDao {
    Customer save(Customer customer);

    Customer findByEmail(String email);

    Customer getReferenceById(UUID id);

    void OTPUpdate(String OTP, Date date, UUID id);
}
