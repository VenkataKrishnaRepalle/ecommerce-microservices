package com.pm.spring.ema.common.util.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CustomerDto {

    private UUID id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String state;

    private String country;

    private String postalCode;

    private EnabledStatus isEnabled;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    public enum EnabledStatus {
        INACTIVE,
        ACTIVE
    }

}
