package com.pm.service.dto.request;

import com.pm.service.dto.validations.ValidUserStatus;
import com.pm.spring.ema.common.util.exception.ErrorCodes;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private String confirmPassword;

    private String photo;

    @ValidUserStatus(message = ErrorCodes.E4009)
    @NotBlank(message = ErrorCodes.E4011)
    private String status;

    @NotNull(message = ErrorCodes.E4010)
    private UUID roleId;
}
