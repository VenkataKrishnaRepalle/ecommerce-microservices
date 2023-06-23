package com.spring6.user.dto;

import com.spring6.common.exeption.ErrorCodes;
import com.spring6.user.entity.Role;
import com.spring6.user.entity.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


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

    private String username;

    private String password;

    private String photo;

    private UserStatus status;

    private Instant createdOn;

    private Instant lastUpdatedOn;

    private Set<Role> roles = new HashSet<>();
}
