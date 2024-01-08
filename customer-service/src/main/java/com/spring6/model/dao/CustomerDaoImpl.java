package com.pm.spring.ema.model.dao;

import com.pm.spring.ema.model.entity.Customer;
import com.pm.spring.ema.model.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class CustomerDaoImpl implements CustomerDao {

    private final CustomerRepository customerRepository;
    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public Customer getReferenceById(UUID id) {
        return  customerRepository.getReferenceById(id);
    }

    @Override
    public void OTPUpdate(String OTP, Date date, UUID id) {
        customerRepository.OTPUpdate(OTP, date, id);
    }
}
