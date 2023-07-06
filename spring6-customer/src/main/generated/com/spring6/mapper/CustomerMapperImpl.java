package com.spring6.mapper;

import com.spring6.dto.CustomerCreateRequestDto;
import com.spring6.dto.CustomerCreateResponseDto;
import com.spring6.dto.CustomerFindResponseDto;
import com.spring6.dto.CustomerRegisterDto;
import com.spring6.dto.LoginDto;
import com.spring6.entity.Customer;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-07T00:16:05+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20 (Oracle Corporation)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public Customer customerFindResponseDtoToCustomer(CustomerFindResponseDto customerFindResponseDto) {
        if ( customerFindResponseDto == null ) {
            return null;
        }

        Customer.CustomerBuilder customer = Customer.builder();

        customer.id( customerFindResponseDto.getId() );
        customer.phoneNumber( customerFindResponseDto.getPhoneNumber() );
        customer.addressLine1( customerFindResponseDto.getAddressLine1() );
        customer.addressLine2( customerFindResponseDto.getAddressLine2() );
        customer.city( customerFindResponseDto.getCity() );
        customer.state( customerFindResponseDto.getState() );
        customer.country( customerFindResponseDto.getCountry() );
        customer.postalCode( customerFindResponseDto.getPostalCode() );
        customer.oneTimePassword( customerFindResponseDto.getOneTimePassword() );
        customer.otpRequestedTime( customerFindResponseDto.getOtpRequestedTime() );
        customer.createdTime( customerFindResponseDto.getCreatedTime() );
        customer.updatedTime( customerFindResponseDto.getUpdatedTime() );

        return customer.build();
    }

    @Override
    public CustomerCreateResponseDto customerToCustomerCreateResponseDto(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerCreateResponseDto.CustomerCreateResponseDtoBuilder customerCreateResponseDto = CustomerCreateResponseDto.builder();

        customerCreateResponseDto.id( customer.getId() );
        customerCreateResponseDto.phoneNumber( customer.getPhoneNumber() );
        customerCreateResponseDto.addressLine1( customer.getAddressLine1() );
        customerCreateResponseDto.addressLine2( customer.getAddressLine2() );
        customerCreateResponseDto.city( customer.getCity() );
        customerCreateResponseDto.state( customer.getState() );
        customerCreateResponseDto.country( customer.getCountry() );
        customerCreateResponseDto.postalCode( customer.getPostalCode() );

        return customerCreateResponseDto.build();
    }

    @Override
    public CustomerRegisterDto customerCreateRequestDtoToCustomerRegisterDto(CustomerCreateRequestDto customerCreateRequestDto) {
        if ( customerCreateRequestDto == null ) {
            return null;
        }

        CustomerRegisterDto.CustomerRegisterDtoBuilder customerRegisterDto = CustomerRegisterDto.builder();

        customerRegisterDto.email( customerCreateRequestDto.getEmail() );
        customerRegisterDto.password( customerCreateRequestDto.getPassword() );
        customerRegisterDto.firstName( customerCreateRequestDto.getFirstName() );
        customerRegisterDto.lastName( customerCreateRequestDto.getLastName() );
        customerRegisterDto.phoneNumber( customerCreateRequestDto.getPhoneNumber() );
        customerRegisterDto.addressLine1( customerCreateRequestDto.getAddressLine1() );
        customerRegisterDto.addressLine2( customerCreateRequestDto.getAddressLine2() );
        customerRegisterDto.city( customerCreateRequestDto.getCity() );
        customerRegisterDto.state( customerCreateRequestDto.getState() );
        customerRegisterDto.country( customerCreateRequestDto.getCountry() );
        customerRegisterDto.postalCode( customerCreateRequestDto.getPostalCode() );
        customerRegisterDto.isEnabled( customerCreateRequestDto.getIsEnabled() );

        return customerRegisterDto.build();
    }

    @Override
    public Customer cutomerRegisterDtoToCustomer(CustomerRegisterDto customerRegisterDto) {
        if ( customerRegisterDto == null ) {
            return null;
        }

        Customer.CustomerBuilder customer = Customer.builder();

        customer.phoneNumber( customerRegisterDto.getPhoneNumber() );
        customer.addressLine1( customerRegisterDto.getAddressLine1() );
        customer.addressLine2( customerRegisterDto.getAddressLine2() );
        customer.city( customerRegisterDto.getCity() );
        customer.state( customerRegisterDto.getState() );
        customer.country( customerRegisterDto.getCountry() );
        customer.postalCode( customerRegisterDto.getPostalCode() );
        customer.oneTimePassword( customerRegisterDto.getOneTimePassword() );
        customer.otpRequestedTime( customerRegisterDto.getOtpRequestedTime() );

        return customer.build();
    }

    @Override
    public CustomerRegisterDto customerToCustomerRegisterDto(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerRegisterDto.CustomerRegisterDtoBuilder customerRegisterDto = CustomerRegisterDto.builder();

        customerRegisterDto.phoneNumber( customer.getPhoneNumber() );
        customerRegisterDto.addressLine1( customer.getAddressLine1() );
        customerRegisterDto.addressLine2( customer.getAddressLine2() );
        customerRegisterDto.city( customer.getCity() );
        customerRegisterDto.state( customer.getState() );
        customerRegisterDto.country( customer.getCountry() );
        customerRegisterDto.postalCode( customer.getPostalCode() );
        customerRegisterDto.oneTimePassword( customer.getOneTimePassword() );
        customerRegisterDto.otpRequestedTime( customer.getOtpRequestedTime() );

        return customerRegisterDto.build();
    }

    @Override
    public CustomerFindResponseDto customerToCustomerFindResponseDto(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerFindResponseDto.CustomerFindResponseDtoBuilder customerFindResponseDto = CustomerFindResponseDto.builder();

        customerFindResponseDto.id( customer.getId() );
        customerFindResponseDto.phoneNumber( customer.getPhoneNumber() );
        customerFindResponseDto.addressLine1( customer.getAddressLine1() );
        customerFindResponseDto.addressLine2( customer.getAddressLine2() );
        customerFindResponseDto.city( customer.getCity() );
        customerFindResponseDto.state( customer.getState() );
        customerFindResponseDto.country( customer.getCountry() );
        customerFindResponseDto.postalCode( customer.getPostalCode() );
        customerFindResponseDto.oneTimePassword( customer.getOneTimePassword() );
        customerFindResponseDto.otpRequestedTime( customer.getOtpRequestedTime() );
        customerFindResponseDto.createdTime( customer.getCreatedTime() );
        customerFindResponseDto.updatedTime( customer.getUpdatedTime() );

        return customerFindResponseDto.build();
    }

    @Override
    public LoginDto customerToLoginDto(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        LoginDto.LoginDtoBuilder loginDto = LoginDto.builder();

        return loginDto.build();
    }
}
