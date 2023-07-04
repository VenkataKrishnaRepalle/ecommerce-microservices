package com.spring6.dto;

import com.spring6.entity.Country;
import com.spring6.entity.EnabledStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class CustomerFindResponseDto {

    @NotNull
    private UUID id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String addressLine1;

    private String addressLine2;

    @NotNull
    private String city;

    @NotNull
    private String state;

    @NotNull
    private String country;

    @NotNull
    private String postalCode;

    @NotNull
    private EnabledStatus isEnabled;

    @NotNull
    private String oneTimePassword;

    @NotNull
    private Date otpRequestedTime;
    @NotNull
    private Date createdTime;

    @NotNull
    private Date updatedTime;
}
