package com.spring6.service;

import com.spring6.dto.CountryFindResponseDto;
import com.spring6.dto.CustomerCreateRequestDto;
import com.spring6.dto.CustomerCreateResponseDto;
import com.spring6.entity.EnabledStatus;
import com.spring6.exception.AlreadyFoundException;
import com.spring6.exception.NotFoundException;
import com.spring6.mapper.CountryMapper;
import com.spring6.mapper.CustomerMapper;
import com.spring6.repository.CountryRepository;
import com.spring6.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CountryRepository countryRepository;

    private final CustomerMapper customerMapper;

    private final CountryMapper countryMapper;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public CustomerCreateResponseDto register(CustomerCreateRequestDto customerCreateRequestDto) {

        if(isCustomerEmailExists(customerCreateRequestDto.getEmail())) {
            throw new AlreadyFoundException("Customer already exists with email : " + customerCreateRequestDto.getEmail());
        }

        if(customerCreateRequestDto.getIsEnabled() == null) {
            customerCreateRequestDto.setIsEnabled(EnabledStatus.INACTIVE);
        }

        CountryFindResponseDto country = countryMapper.countryToCountryFindResponseDto(countryRepository.getCountryByName(customerCreateRequestDto.getCountry().toLowerCase()));
        if(country == null){
            throw new NotFoundException("Could not found country of name : " +customerCreateRequestDto.getCountry());
        }
        customerCreateRequestDto.setCountry(country.getName());
        customerCreateRequestDto.setPassword(passwordEncoder.encode(customerCreateRequestDto.getPassword()));
        return customerMapper.customerToCustomerCreateResponseDto(customerRepository.save(customerMapper.customerCreateRequestDtoToCustomer(customerCreateRequestDto)));
    }

    public boolean isCustomerEmailExists(String email) {
        if(customerRepository.findByEmail(email) != null) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
