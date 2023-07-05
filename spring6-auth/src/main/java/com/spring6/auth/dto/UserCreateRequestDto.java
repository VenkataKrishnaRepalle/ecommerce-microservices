package com.spring6.auth.dto;

import com.spring6.auth.enums.RoleType;
import com.spring6.auth.enums.UserStatus;
import com.spring6.common.exeption.ErrorCodes;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
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

    @NotBlank(message = ErrorCodes.E4011)
    private UserStatus status = UserStatus.ACTIVE;

    @NotNull(message = ErrorCodes.E4010)
    private List<RoleType> roles = new ArrayList<>();
}
