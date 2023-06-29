package com.spring6.user.dto;

import com.spring6.common.exeption.ErrorCodes;
import com.spring6.user.entity.UserStatus;
import com.spring6.user.validations.ConfirmPassword;
import com.spring6.user.validations.ValidUserStatus;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;


@Data
@Builder
public class UserCreateRequestDto {

    @NotBlank(message = ErrorCodes.E4001)
    private String firstName;
    @NotBlank(message = ErrorCodes.E4002)
    private String lastName;

    @NotBlank(message = ErrorCodes.E4003)
    @Email(message = ErrorCodes.E4004)
    private String email;

    @NotBlank(message = ErrorCodes.E4005)
    private String username;

    @NotBlank(message = ErrorCodes.E4006)
    private String password;

    @NotBlank(message = ErrorCodes.E4007)
//    @ConfirmPassword(message = ErrorCodes.E4008)
    private String confirmPassword;

    private String photo;

    @ValidUserStatus(message = ErrorCodes.E4009)
    private UserStatus status;

    @NotNull(message = ErrorCodes.E4010)
    private UUID roleId;
}
