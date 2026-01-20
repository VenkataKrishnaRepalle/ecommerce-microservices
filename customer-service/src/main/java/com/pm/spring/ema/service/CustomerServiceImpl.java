package com.pm.spring.ema.service;

import com.pm.spring.ema.common.util.dto.ApiResponse;
import com.pm.spring.ema.common.util.exception.InvalidInputException;
import com.pm.spring.ema.dto.*;
import com.pm.spring.ema.feign.MailServiceFeign;
import com.pm.spring.ema.modal.Country;
import com.pm.spring.ema.modal.Customer;
import com.pm.spring.ema.modal.EnabledStatus;
import com.pm.spring.ema.exception.AlreadyFoundException;
import com.pm.spring.ema.exception.LoginException;
import com.pm.spring.ema.exception.NotFoundException;
import com.pm.spring.ema.mapper.CountryMapper;
import com.pm.spring.ema.mapper.CustomerMapper;
import com.pm.spring.ema.repository.CountryRepository;
import com.pm.spring.ema.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CountryRepository countryRepository;

    private final CustomerMapper customerMapper;

    private final CountryMapper countryMapper;

    private final MailServiceFeign mailService;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ApiResponse<CustomerDto> getById(UUID userId) {
        return customerRepository.findById(userId)
                .map(customer -> ApiResponse.success(customerMapper.customerToCustomerDto(customer)))
                .orElseGet(() -> ApiResponse.error("Customer not found with id: " + userId));
    }

    public ApiResponse<CustomerDto> register(CustomerDto customerDto) {

        if (isCustomerEmailExists(customerDto.getEmail())) {
            return ApiResponse.error("Customer already exists with email : " + customerDto.getEmail());
        }

        if (customerDto.getIsEnabled() == null) {
            customerDto.setIsEnabled(EnabledStatus.INACTIVE);
        }

        Country country = countryRepository.getCountryByName(customerDto.getCountry().toLowerCase());
        if (country == null) {
            return ApiResponse.error("Could not found country of name : " + customerDto.getCountry());
        }

        customerDto.setPhoneNumber(getCountryCode(country.getName()) + customerDto.getPhoneNumber());
        customerDto.setCountry(country.getName());
        customerDto.setPassword(passwordEncoder.encode(customerDto.getPassword()));

        return ApiResponse.success(customerMapper.customerToCustomerDto(
                customerRepository.save(customerMapper.cutomerRegisterDtoToCustomer(customerDto))));
    }


    public void login(LoginDto loginDto) {
        var customer = customerRepository.findByEmail(loginDto.getEmail());
        if (customer == null) {
            throw new NotFoundException("could not found account with email: " + loginDto.getEmail());
        }
        if (customer.getEmail().equals(loginDto.getEmail()) && passwordEncoder.matches(loginDto.getPassword(), customer.getPassword())) {
            mailService.sendOTP(customer.getId());
        } else {
            throw new LoginException("Invalid Credentials");
        }
    }

    public Enum<EnabledStatus> getIsEnabledStatus(UUID id) {
        var customer = customerRepository.getReferenceById(id);
        return customer.getIsEnabled();
    }

    public void forgotPassword(ForgotPasswordDto forgotPasswordDto) {
        var customer = customerRepository.findByEmail(forgotPasswordDto.getEmail());
        System.out.println(customer);
        if (customer != null && forgotPasswordDto.getPassword().equals(forgotPasswordDto.getConfirmPassword())) {
            if (passwordEncoder.matches(forgotPasswordDto.getPassword(), customer.getPassword())) {
                throw new LoginException("Entered password is same as old password, Please Enter a new Password");
            }
            customer.setPassword(passwordEncoder.encode(forgotPasswordDto.getPassword()));
            customerRepository.save(customer);
        } else if (!forgotPasswordDto.getPassword().equals(forgotPasswordDto.getConfirmPassword())) {
            throw new LoginException("New Password and Confirm Password not matched");
        }

    }

    public void changePassword(ChangePasswordDto changePasswordDto) {
        var customer = customerRepository.findByEmail(changePasswordDto.getEmail());
        System.out.println(customer);
        if (customer != null && passwordEncoder.matches(changePasswordDto.getOldPassword(), customer.getPassword())) {
            if (changePasswordDto.getOldPassword().equals(changePasswordDto.getNewPassword())) {
                throw new LoginException("Old Password and New password Matched, Please Enter a different new Password");
            } else if (changePasswordDto.getOldPassword().equals(changePasswordDto.getConfirmNewPassword())) {
                throw new LoginException("Old Password and Confirm New password Matched, Please Enter a different Confirm New Password");
            } else if (changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmNewPassword())) {
                customer.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
                customerRepository.save(customer);
            } else {
                throw new LoginException("New Password and Confirm new Password not matched");
            }
        } else if (customer != null && !passwordEncoder.matches(changePasswordDto.getOldPassword(), customer.getPassword())) {
            throw new LoginException("Entered Old Password is incorrect, Please provide correct Old Password");
        } else {
            throw new NotFoundException("Invalid Customer");
        }
    }

    public void isEmailExists(String email) {
        if (!isCustomerEmailExists(email)) {
            throw new NotFoundException("could not found account with email " + email);
        }
    }

    public boolean isCustomerEmailExists(String email) {
        if (customerRepository.findByEmail(email) != null) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public String getCountryCode(String countryName) {
        CountryDto country = countryMapper.countryToCountryFindResponseDto(countryRepository.getCountryByName(countryName.toLowerCase()));
        return country.getCode();
    }
}
