package com.pm.spring.ema.mailservice.controller;

import com.pm.spring.ema.common.util.HttpStatusCodes;
import com.pm.spring.ema.common.util.api.ErrorResponse;
import com.pm.spring.ema.mailservice.model.OtpType;
import com.pm.spring.ema.mailservice.service.MailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MailController {

  private final MailService mailService;

  @Operation(tags = "Mail", summary = "Send Login OTP", description = "Send Login OTP to Email")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = HttpStatusCodes.OK,
            description = "Send Login OTP",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HttpStatus.class))),
        @ApiResponse(
            responseCode = HttpStatusCodes.BAD_REQUEST,
            description = "Bad Request",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR,
            description = "Internal Server Error",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  @PostMapping("/send-login-otp/{userId}")
  public ResponseEntity<Void> sendLoginOtp(@PathVariable @NotNull UUID userId) {
    log.info("MailController:sendLoginOtp Execution Started");

    mailService.sendLoginOtp(userId);

    log.info("MailController:sendLoginOtp OTP sent Successfully for userId : {}", userId);
    log.info("MailController:sendLoginOtp Execution Ended");

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/send-otp/{userId}")
  public ResponseEntity<Void> sendOtp(@PathVariable UUID userId, @RequestBody OtpType otpType) {
    log.info("MailController:sendOtp Execution Started");
    mailService.createOtp(userId, otpType);
    log.info("MailController:sendOtp Execution Ended");

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Operation(tags = "Mail", summary = "OTP Validation", description = "OTP Validation")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = HttpStatusCodes.OK,
            description = "OTP Validation",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HttpStatus.class))),
        @ApiResponse(
            responseCode = HttpStatusCodes.BAD_REQUEST,
            description = "Bad Request",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR,
            description = "Internal Server Error",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  @PostMapping("/otp-validation/{userId}")
  public ResponseEntity<HttpStatus> otpValidation(
      @PathVariable @NotNull UUID userId,
      @RequestParam("otp") Long otp,
      @RequestParam("type") OtpType type) {
    log.info("MailController:otpValidation Execution Started");

    mailService.otpValidation(userId, otp, type);

    log.info("MailController:otpValidation otpValidation Successful for userId : {}", userId);
    log.info("MailController:otpValidation Execution Ended");

    return ResponseEntity.ok(HttpStatus.OK);
  }

  @Operation(
      tags = "Mail",
      summary = "Send Forgot Password OTP",
      description = "Send Forgot Password OTP to Email")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = HttpStatusCodes.OK,
            description = "Send Forgot Password OTP",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HttpStatus.class))),
        @ApiResponse(
            responseCode = HttpStatusCodes.BAD_REQUEST,
            description = "Bad Request",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR,
            description = "Internal Server Error",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  @PostMapping("/send-forgot-password-otp/{userId}")
  public ResponseEntity<HttpStatus> sendForgotPasswordOtp(@PathVariable @NotNull UUID userId) {
    log.info("MailController:sendForgotPasswordOtp Execution Started");

    mailService.createOtp(userId, OtpType.FORGOT_PASSWORD_OTP);
    mailService.sendForgotPasswordOtp(userId);

    log.info("MailController:sendForgotPasswordOtp OTP sent Successfully for userId : {}", userId);
    log.info("MailController:sendForgotPasswordOtp Execution Ended");

    return ResponseEntity.ok(HttpStatus.OK);
  }
}
