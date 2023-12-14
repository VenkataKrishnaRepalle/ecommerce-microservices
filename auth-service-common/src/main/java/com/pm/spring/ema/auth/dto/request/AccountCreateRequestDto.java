package com.pm.spring.ema.auth.dto.request;

import com.pm.spring.ema.auth.dto.enums.AccountStatus;
import com.pm.spring.ema.auth.dto.enums.RoleType;
import com.pm.spring.ema.common.util.exception.ErrorCodes;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@Builder
public class AccountCreateRequestDto {

    @NotBlank(message = ErrorCodes.E4001)
    private String firstName;

    @NotBlank(message = ErrorCodes.E4002)
    private String lastName;

    @NotBlank(message = ErrorCodes.E4003)
    @Email(message = ErrorCodes.E4004)
    private String email;

    @NotBlank(message = ErrorCodes.E4005)
    @Size(min =5,max = 15, message = ErrorCodes.E4014)
    @Max(value = 5, message = ErrorCodes.E4014)
    private String username;

    @NotBlank(message = ErrorCodes.E4006)
    private String password;

    @NotBlank(message = ErrorCodes.E4007)
    private String confirmPassword;

    @NotBlank(message = ErrorCodes.E4011)
    private AccountStatus status = AccountStatus.ACTIVE;

    @NotNull(message = ErrorCodes.E4010)
    private List<RoleType> roles = new ArrayList<>();
}
