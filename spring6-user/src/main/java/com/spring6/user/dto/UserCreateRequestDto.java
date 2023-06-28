package com.spring6.user.dto;

import com.spring6.user.entity.Role;
import com.spring6.user.entity.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import java.util.UUID;


@Data
@Builder
public class UserCreateRequestDto {

    @NotBlank(message = "E1001")
    private String firstName;
    @NotBlank
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String photo;

    @NotNull
    private UserStatus status;

    @NotNull
    private UUID roleId;
}
