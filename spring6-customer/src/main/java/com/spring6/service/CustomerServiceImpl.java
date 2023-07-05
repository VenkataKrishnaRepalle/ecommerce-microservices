package com.spring6.service;

import com.spring6.dto.*;
import com.spring6.enums.EnabledStatus;
import com.spring6.exception.AlreadyFoundException;
import com.spring6.exception.LoginException;
import com.spring6.exception.NotFoundException;
import com.spring6.mapper.CountryMapper;
import com.spring6.mapper.CustomerMapper;
import com.spring6.repository.CountryRepository;
import com.spring6.repository.CustomerRepository;
import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import net.bytebuddy.utility.RandomString;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private static final long OTP_VALID_DURATION = 5 * 60 * 1000;     // 5 minutes

    private final CustomerRepository customerRepository;

    private final CountryRepository countryRepository;

    private final CustomerMapper customerMapper;

    private final CountryMapper countryMapper;

    private final JavaMailSender mailSender;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public CustomerCreateResponseDto register(CustomerCreateRequestDto customerCreateRequestDto) {

        CustomerRegisterDto customerRegisterDto = customerMapper.customerCreateRequestDtoToCustomerRegisterDto(customerCreateRequestDto);
        if(isCustomerEmailExists(customerRegisterDto.getEmail())) {
            throw new AlreadyFoundException("Customer already exists with email : " + customerRegisterDto.getEmail());
        }

        if(customerRegisterDto.getIsEnabled() == null) {
            customerRegisterDto.setIsEnabled(EnabledStatus.INACTIVE);
        }

        CountryFindResponseDto country = countryMapper.countryToCountryFindResponseDto(countryRepository.getCountryByName(customerRegisterDto.getCountry().toLowerCase()));
        if(country == null){
            throw new NotFoundException("Could not found country of name : " +customerRegisterDto.getCountry());
        }

        customerRegisterDto.setPhoneNumber(getCountryCode(country.getName()) + customerRegisterDto.getPhoneNumber());

        customerRegisterDto.setCountry(country.getName());

        customerRegisterDto.setPassword(passwordEncoder.encode(customerRegisterDto.getPassword()));

        Map<String, Object> otp = generateOTP();
        customerRegisterDto.setOneTimePassword((String) otp.get("OTP"));
        customerRegisterDto.setOtpRequestedTime((Date) otp.get("date"));

        return customerMapper.customerToCustomerCreateResponseDto(customerRepository.save(customerMapper.cutomerRegisterDtoToCustomer(customerRegisterDto)));
    }


    public void login(LoginDto loginDto) throws Exception {
        CustomerFindResponseDto customer = customerMapper.customerToCustomerFindResponseDto(customerRepository.findByEmail(loginDto.getEmail()));
        System.out.println(customer);
        if(customer.getEmail().equals(loginDto.getEmail()) && passwordEncoder.matches(loginDto.getPassword(), customer.getPassword())) {
            long currentTimeInMillis = System.currentTimeMillis();
            long otpRequestedTime = customer.getOtpRequestedTime().getTime();
            if(otpRequestedTime + OTP_VALID_DURATION < currentTimeInMillis){
                Map<String, Object> otp = generateOTP();
                customerRepository.OTPUpdate((String) otp.get("OTP"), (Date) otp.get("date"), customer.getId());
            }
            sendOTPEmail(customer.getId());
            sendOTPMobileNumber(customer.getId());
        }
        else {
            throw new LoginException("Invalid Credentials");
        }
    }
    public Enum<EnabledStatus> getIsEnabledStatus(UUID id) {
        CustomerRegisterDto customer = customerMapper.customerToCustomerRegisterDto(customerRepository.getReferenceById(id));

        return customer.getIsEnabled();
    }

    public void OTPValidation(UUID id, String otp) {
        CustomerFindResponseDto customer = customerMapper.customerToCustomerFindResponseDto(customerRepository.getReferenceById(id));

        long currentTimeInMillis = System.currentTimeMillis();
        long otpRequestedTime = customer.getOtpRequestedTime().getTime();
        System.out.println(otpRequestedTime);

        if(customer.getOneTimePassword().equals(otp) && otpRequestedTime + OTP_VALID_DURATION > currentTimeInMillis) {
            System.out.println("OTP Validated Succesfully");
            if(customer.getIsEnabled() == EnabledStatus.INACTIVE){
                customer.setIsEnabled(EnabledStatus.ACTIVE);
               customerRepository.save(customerMapper.customerFindResponseDtoToCustomer(customer));
                System.out.println("Account Activated now");
            }
        } else if (otpRequestedTime + OTP_VALID_DURATION < currentTimeInMillis) {
            Map<String, Object> otp1 = generateOTP();
            customerRepository.OTPUpdate((String) otp1.get("OTP"), (Date) otp1.get("date"), customer.getId());
            OTPValidation(id, String.valueOf(otp1.get("OTP")));
        } else if (!customer.getOneTimePassword().equals(otp)) {
            System.out.println("Invalid OTP Entered");
        }
    }


    public boolean isCustomerEmailExists(String email) {
        if(customerRepository.findByEmail(email) != null) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Map<String, Object> generateOTP() {
        String OTP = RandomString.make(8);

        Date date = new Date();

        Map<String, Object> result = new HashMap<>();
        result.put("OTP", OTP);
        result.put("date", date);

        return result;
    }

    public void sendOTPEmail(UUID id) throws MessagingException, UnsupportedEncodingException {
        CustomerRegisterDto customerRegisterDto = customerMapper.customerToCustomerRegisterDto(customerRepository.getReferenceById(id));

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("v4431365@gmail.com", "OTP Validation");
        helper.setTo(customerRegisterDto.getEmail());

        String subject = "Here's your One Time Password ("+ customerRegisterDto.getOneTimePassword() +") - Expire in 5 minutes!";
        String content = null;

        if(customerRegisterDto.getIsEnabled().equals(EnabledStatus.INACTIVE)) {
            content = "<p>Hello <b>" + customerRegisterDto.getFirstName() + "</b>,</p>"
                    + "<p>For security reason, you're required to use the following "
                    + "One Time Password to activate your account:</p>"
                    + "<p><b>" + customerRegisterDto.getOneTimePassword() + "</b></p>"
                    + "<br>"
                    + "<p><b>or</b></p>"
                    + "<a href = http://localhost:1003/customer/OTPValidation/"+ id+ "?OTP="+ customerRegisterDto.getOneTimePassword() + "> Click Here</a>"
                    + "<p>to directly activate your account</p><br>"
                    + "<p>Note: this OTP is set to expire in 5 minutes.</p>";
        }
        else {
            content = "<p>Hello <b>" + customerRegisterDto.getFirstName() + "</b>,</p>"
                    + "<p>For security reason, you're required to use the following "
                    + "One Time Password to Login:</p>"
                    + "<p><b>" + customerRegisterDto.getOneTimePassword() + "</b></p>"
                    + "<br>"
                    + "<p>Note: this OTP is set to expire in 5 minutes.</p>";
        }

        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }

    public void sendOTPMobileNumber(UUID id) throws Exception{
        CustomerRegisterDto customerRegisterDto = customerMapper.customerToCustomerRegisterDto(customerRepository.getReferenceById(id));

        Twilio.init("AC443909b8a51ab7f21ac55e853e7b4d4b", "1eba97540b14fc0ac9be1c12a311da03");
        try {
            Message.creator(new PhoneNumber(customerRegisterDto.getPhoneNumber()), new PhoneNumber("+14175453283"), "Hi "+ customerRegisterDto.getFirstName() + ",\n Your One-Time Password is " + customerRegisterDto.getOneTimePassword() + ".\n Note: this OTP is set to expire in 5 minutes.").create();
        }
        catch(ApiException e) {
            System.err.println(e.getMessage());
        }
    }

    public String getCountryCode(String countryName) {
        CountryFindResponseDto country = countryMapper.countryToCountryFindResponseDto(countryRepository.getCountryByName(countryName));
        return country.getCode();
    }
}
