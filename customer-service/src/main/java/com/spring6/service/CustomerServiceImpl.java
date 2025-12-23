package com.pm.spring.ema.permission;

import com.pm.spring.ema.dto.*;
import com.pm.spring.ema.enums.EnabledStatus;
import com.pm.spring.ema.exception.AlreadyFoundException;
import com.pm.spring.ema.exception.LoginException;
import com.pm.spring.ema.exception.NotFoundException;
import com.pm.spring.ema.mapper.CountryMapper;
import com.pm.spring.ema.mapper.CustomerMapper;
import com.pm.spring.ema.model.dao.CountryDao;
import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.pm.spring.ema.model.dao.CustomerDao;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final CustomerDao customerDao;

    private final CountryDao countryDao;

    private final CustomerMapper customerMapper;

    private final CountryMapper countryMapper;

    private final JavaMailSender mailSender;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public CustomerCreateResponseDto register(CustomerCreateRequestDto customerCreateRequestDto) {

        CustomerDto customerRegisterDto = customerMapper.customerCreateRequestDtoToCustomerRegisterDto(customerCreateRequestDto);
        if (isCustomerEmailExists(customerRegisterDto.getEmail())) {
            throw new AlreadyFoundException("Customer already exists with email : " + customerRegisterDto.getEmail());
        }

        if (customerRegisterDto.getIsEnabled() == null) {
            customerRegisterDto.setIsEnabled(EnabledStatus.INACTIVE);
        }

        CountryFindResponseDto country = countryMapper.countryToCountryFindResponseDto(countryDao.getCountryByName(customerRegisterDto.getCountry().toLowerCase()));
        if (country == null) {
            throw new NotFoundException("Could not found country of name : " + customerRegisterDto.getCountry());
        }

        customerRegisterDto.setPhoneNumber(getCountryCode(country.getName()) + customerRegisterDto.getPhoneNumber());

        customerRegisterDto.setCountry(country.getName());

        customerRegisterDto.setPassword(passwordEncoder.encode(customerRegisterDto.getPassword()));

        Map<String, Object> otp = generateOTP();
        customerRegisterDto.setOneTimePassword((String) otp.get("OTP"));
        customerRegisterDto.setOtpRequestedTime((Date) otp.get("date"));

        return customerMapper.customerToCustomerCreateResponseDto(customerDao.save(customerMapper.cutomerRegisterDtoToCustomer(customerRegisterDto)));
    }


    public void login(LoginDto loginDto) throws Exception {
        CustomerFindResponseDto customer = customerMapper.customerToCustomerFindResponseDto(customerDao.findByEmail(loginDto.getEmail()));
        if(customer == null) {
            throw new NotFoundException("could not found account with email: " + loginDto.getEmail());
        }
        if (customer.getEmail().equals(loginDto.getEmail()) && passwordEncoder.matches(loginDto.getPassword(), customer.getPassword())) {
            if (otpValidDuration(customer.getOtpRequestedTime().getTime())) {
                Map<String, Object> otp = generateOTP();
                customer.setOneTimePassword((String) otp.get("OTP"));
                customer.setOtpRequestedTime((Date) otp.get(("date")));
                customerDao.save(customerMapper.customerFindResponseDtoToCustomer(customer));
            }
            sendOTPEmail(customer.getId());
            sendOTPMobileNumber(customer.getId());
        } else {
            throw new LoginException("Invalid Credentials");
        }
    }

    public boolean otpValidDuration(long otpRequestedTime) {
        long currentTimeInMillis = System.currentTimeMillis();
        if (otpRequestedTime + OTP_VALID_DURATION >= currentTimeInMillis) {
            return false;
        }
        return true;
    }

    public Enum<EnabledStatus> getIsEnabledStatus(UUID id) {
        CustomerDto customer = customerMapper.customerToCustomerRegisterDto(customerDao.getReferenceById(id));

        return customer.getIsEnabled();
    }

    public void OTPValidation(UUID id, String otp) {
        CustomerFindResponseDto customer = customerMapper.customerToCustomerFindResponseDto(customerDao.getReferenceById(id));

        long currentTimeInMillis = System.currentTimeMillis();
        long otpRequestedTime = customer.getOtpRequestedTime().getTime();
        System.out.println(otpRequestedTime);

        if (customer.getOneTimePassword().equals(otp) && otpRequestedTime + OTP_VALID_DURATION > currentTimeInMillis) {
            System.out.println("OTP Validated Successfully");
            if (customer.getIsEnabled() == EnabledStatus.INACTIVE) {
                customer.setIsEnabled(EnabledStatus.ACTIVE);
                customerDao.save(customerMapper.customerFindResponseDtoToCustomer(customer));
                System.out.println("Account Activated now");
            }
        } else if (otpRequestedTime + OTP_VALID_DURATION < currentTimeInMillis) {
            Map<String, Object> otp1 = generateOTP();
            customerDao.OTPUpdate((String) otp1.get("OTP"), (Date) otp1.get("date"), customer.getId());
            OTPValidation(id, String.valueOf(otp1.get("OTP")));
        } else if (!customer.getOneTimePassword().equals(otp)) {
            System.out.println("Invalid OTP Entered");
        }
    }

    public void forgotPassword(String email, ForgotPasswordDto forgotPasswordDto) {
        CustomerFindResponseDto customer = customerMapper.customerToCustomerFindResponseDto(customerDao.findByEmail(email));
        System.out.println(customer);
        if (customer != null && forgotPasswordDto.getPassword().equals(forgotPasswordDto.getConfirmPassword())) {
            if (passwordEncoder.matches(forgotPasswordDto.getPassword(), customer.getPassword())) {
                throw new LoginException("Entered password is same as old password, Please Enter a new Password");
            }
            customer.setPassword(passwordEncoder.encode(forgotPasswordDto.getPassword()));
            customerDao.save(customerMapper.customerFindResponseDtoToCustomer(customer));
        } else if (!forgotPasswordDto.getPassword().equals(forgotPasswordDto.getConfirmPassword())) {
            throw new LoginException("New Password and Confirm Password not matched");
        }

    }

    public void changePassword(String email, ChangePasswordDto changePasswordDto) {
        CustomerFindResponseDto customer = customerMapper.customerToCustomerFindResponseDto(customerDao.findByEmail(email));
        System.out.println(customer);
        if (customer != null && passwordEncoder.matches(changePasswordDto.getOldPassword(), customer.getPassword())) {
            if (changePasswordDto.getOldPassword().equals(changePasswordDto.getNewPassword())) {
                throw new LoginException("Old Password and New password Matched, Please Enter a different new Password");
            } else if (changePasswordDto.getOldPassword().equals(changePasswordDto.getConfirmNewPassword())) {
                throw new LoginException("Old Password and Confirm New password Matched, Please Enter a different Confirm New Password");
            } else if (changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmNewPassword())) {
                customer.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
                customerDao.save(customerMapper.customerFindResponseDtoToCustomer(customer));
            } else if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmNewPassword())) {
                throw new LoginException("New Password and Confirm new Password not matched");
            }
        } else if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), customer.getPassword())) {
            throw new LoginException("Entered Old Password is incorrect, Please provide correct Old Password");
        }
    }

    public void isEmailExists(String email) {
        if (!isCustomerEmailExists(email)) {
            throw new NotFoundException("could not found account with email " + email);
        }
    }

    public boolean isCustomerEmailExists(String email) {
        if (customerDao.findByEmail(email) != null) {
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
        CustomerDto customerRegisterDto = customerMapper.customerToCustomerRegisterDto(customerDao.getReferenceById(id));

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("v4431365@gmail.com", "OTP Validation");
        helper.setTo(customerRegisterDto.getEmail());

        String subject = "Here's your One Time Password (" + customerRegisterDto.getOneTimePassword() + ") - Expire in 5 minutes!";
        String content = null;

        if (customerRegisterDto.getIsEnabled().equals(EnabledStatus.INACTIVE)) {
            content = "<p>Hello <b>" + customerRegisterDto.getFirstName() + "</b>,</p>"
                    + "<p>For security reason, you're required to use the following "
                    + "One Time Password to activate your account:</p>"
                    + "<p><b>" + customerRegisterDto.getOneTimePassword() + "</b></p>"
                    + "<br>"
                    + "<p><b>or</b></p>"
                    + "<a href = http://localhost:1003/customer/OTPValidation/" + id + "?OTP=" + customerRegisterDto.getOneTimePassword() + "> Click Here</a>"
                    + "<p>to directly activate your account</p><br>"
                    + "<p>Note: this OTP is set to expire in 5 minutes.</p>";
        } else {
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

    public void sendOTPMobileNumber(UUID id) throws Exception {
        CustomerDto customerRegisterDto = customerMapper.customerToCustomerRegisterDto(customerDao.getReferenceById(id));

        Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("TWILIO_AUTH_TOKEN"));
        try {
            Message.creator(new PhoneNumber(customerRegisterDto.getPhoneNumber()), new PhoneNumber("+14175453283"), "Hi " + customerRegisterDto.getFirstName() + ",\n Your One-Time Password is " + customerRegisterDto.getOneTimePassword() + ".\n Note: this OTP is set to expire in 5 minutes.").create();
        } catch (ApiException e) {
            System.err.println(e.getMessage());
        }
    }

    public String getCountryCode(String countryName) {
        CountryFindResponseDto country = countryMapper.countryToCountryFindResponseDto(countryDao.getCountryByName(countryName.toLowerCase()));
        return country.getCode();
    }
}
