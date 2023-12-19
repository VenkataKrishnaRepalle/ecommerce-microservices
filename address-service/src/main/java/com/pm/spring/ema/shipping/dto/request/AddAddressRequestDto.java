package com.pm.spring.ema.shipping.dto.request;

import com.pm.spring.ema.common.util.exception.ErrorCodes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class AddAddressRequestDto {

    @NotBlank(message = ErrorCodes.E5001)
    private String country;

    @NotBlank(message = ErrorCodes.E5001)
    private String fullName;

    @NotBlank(message = ErrorCodes.E5001)
    @Size(min = 10, max = 10, message = ErrorCodes.E5002)
    private String mobileNumber;

    @NotBlank(message = ErrorCodes.E5001)
    @Size(min = 6, max = 6, message = ErrorCodes.E5003)
    private String pincode;

    @NotBlank(message = ErrorCodes.E5001)
    @Size(max = 20)
    private String houseNumber;

    @NotBlank(message = ErrorCodes.E5001)
    @Size(max = 30)
    private String area;

    @NotBlank(message = ErrorCodes.E5001)
    @Size(max = 30)
    private String landmark;

    @NotBlank(message = ErrorCodes.E5001)
    private String city;

    @NotBlank(message = ErrorCodes.E5001)
    private String state;

    private Boolean defaultAddress = Boolean.FALSE;
}
