package com.pm.spring.ema.dto.response;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressFindResponseDto {

    private UUID id;

    private UUID userUuid;

    private String country;

    private String fullName;

    @Size(min = 10, max = 10)
    private String mobileNumber;

    @Size(min = 6, max = 6)
    private String pincode;

    @Size(max = 20)
    private String houseNumber;

    @Size(max = 30)
    private String area;

    @Size(max = 30)
    private String landmark;

    private String city;

    private String state;

    private Boolean defaultAddress;
}
