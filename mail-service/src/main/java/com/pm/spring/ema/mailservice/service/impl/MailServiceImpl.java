package com.pm.spring.ema.mailservice.service.impl;

import com.pm.spring.ema.common.util.dto.CustomerDto;
import com.pm.spring.ema.common.util.exception.utils.ErrorCodes;
import com.pm.spring.ema.common.util.exception.InvalidInputException;
import com.pm.spring.ema.mailservice.model.Otp;
import com.pm.spring.ema.mailservice.service.MailService;
import com.pm.spring.ema.mailservice.feign.UserServiceFeignClient;
import com.pm.spring.ema.mailservice.model.OtpStatus;
import com.pm.spring.ema.mailservice.model.OtpType;
import com.pm.spring.ema.mailservice.repository.OtpRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    private final OtpRepository otpRepository;

    private final UserServiceFeignClient userServiceFeignClient;

    private static final long OTP_VALID_DURATION = 2 * 60 * 1000;

    //    todo: change @value to logic app-config-data configuration
    @Value("${email.template.login}")
    private String emailLoginTemplateName;

    @Value("${email.template.forgot-password}")
    private String emailForgotPasswordTemplateName;

    @Value("${default.send.email.address}")
    private String email;

    @Value("${default.send.email.header}")
    private String emailHeader;

    @Override
    public Otp createOtp(UUID userId, OtpType type) {
        log.info("MailService:createOtp Execution Started");
        var createOtp = Otp.builder()
                .otpNumber(randomNumber())
                .userUuid(userId)
                .type(type)
                .build();

        var otpList = otpRepository.getAllByUserUuidAndStatusAndType(userId, OtpStatus.ACTIVE, type);
        for (Otp otp : otpList) {
            otp.setStatus(OtpStatus.EXPIRED);
        }
        otpRepository.saveAll(otpList);
        otpRepository.save(createOtp);
        log.info("MailService:createOtp Execution Ended");
        return createOtp;
    }

    public void sendLoginOtp(UUID id) throws MessagingException, UnsupportedEncodingException {
        log.info("MailService:sendLoginOtp Execution Started");

        var user = getUserByUserId(id);
        var otp = otpRepository.findByUserUuidAndStatusAndType(id, OtpStatus.ACTIVE, OtpType.LOGIN_OTP);

        if (otp.isEmpty()) {
            log.error("MailService:sendLoginOtp errorMessage : {}", ErrorCodes.E1001);
            log.info("MailService:sendLoginOtp Execution Ended");
            throw new InvalidInputException(ErrorCodes.E3001);
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(email, emailHeader);
        helper.setTo(user.getEmail());

        helper.setSubject("Here is your One Time Password for login - Expire in 2 minutes!");

        Context context = new Context();
        context.setVariable("name", user.getFirstName() + " " + user.getLastName());
        context.setVariable("otp", otp.get().getOtpNumber());

        helper.setText(templateEngine.process(emailLoginTemplateName, context), true);

        mailSender.send(message);

        log.info("MailService:sendLoginOtp Mail sent successfully");
        log.info("MailService:sendLoginOtp Execution Ended");
    }

    public void sendForgotPasswordOtp(UUID id) throws MessagingException, UnsupportedEncodingException {
        log.info("MailService:sendForgotPasswordOtp Execution Started");

        var user = getUserByUserId(id);
        var otp = otpRepository.findByUserUuidAndStatusAndType(id, OtpStatus.ACTIVE, OtpType.FORGOT_PASSWORD_OTP);
        if (otp.isEmpty()) {
            log.error("MailService:sendForgotPasswordOtp errorMessage : {}", ErrorCodes.E1001);
            throw new InvalidInputException(ErrorCodes.E3001);
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(email, emailHeader);
        helper.setTo(user.getEmail());
        //            todo: add & read message from configuration

        helper.setSubject("Here is your One Time Password for Forgot Password - Expire in 2 minutes");

        Context context = new Context();
        context.setVariable("name", user.getFirstName() + " " + user.getLastName());
        context.setVariable("otp", otp.get().getOtpNumber());

        helper.setText(templateEngine.process(emailForgotPasswordTemplateName, context), true);

        mailSender.send(message);

        log.info("MailService:sendForgotPasswordOtp Mail sent successfully");
        log.info("MailService:sendForgotPasswordOtp Execution Ended");
    }

    @Override
    public void otpValidation(UUID userId, Long otp, OtpType type) {
        log.info("MailService:otpValidation Execution Started");

        var userOtp = otpRepository.findByUserUuidAndStatusAndType(userId, OtpStatus.ACTIVE, type);
        if (userOtp.isEmpty()) {
            log.error("MailService:otpValidation errorMessage : {}", ErrorCodes.E3003);
            log.info("MailService:otpValidation Execution Ended");
            throw new InvalidInputException(ErrorCodes.E3003);
        }
        if (!userOtp.get().getOtpNumber().equals(otp)) {
            log.error("MailService:otpValidation errorMessage : {}", ErrorCodes.E3004);
            log.info("MailService:otpValidation Execution Ended");
            throw new InvalidInputException(ErrorCodes.E3004);
        }

        if (userOtp.get().getCreatedTime().getTime() + OTP_VALID_DURATION < System.currentTimeMillis()) {
            log.error("MailService:otpValidation errorMessage : {}", ErrorCodes.E8001);
            log.info("MailService:otpValidation Execution Ended");
            throw new InvalidInputException(ErrorCodes.E8001);
        }

        var activeOtp = userOtp.get();
        activeOtp.setStatus(OtpStatus.USED);
        otpRepository.save(activeOtp);

        log.info("MailService:otpValidation OTP validation Successfull");
        log.info("MailService:otpValidation Execution Ended");
    }

    private Long randomNumber() {
        Random random = new Random();
        return random.nextLong(100000, 999999);
    }

    private CustomerDto getUserByUserId(UUID id) {
        return userServiceFeignClient.getById(id);
    }
}
