package com.pm.spring.ema.dto;

import com.pm.spring.ema.common.util.exception.utils.ErrorCodes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class AddressDto {

  private UUID id;

  private UUID userUuid;

  @NotBlank(message = ErrorCodes.E5001)
  private String country;

  @NotBlank(message = ErrorCodes.E5004)
  private String fullName;

  @NotBlank(message = ErrorCodes.E5005)
  @Size(min = 10, max = 10, message = ErrorCodes.E5002)
  private String mobileNumber;

  @NotBlank(message = ErrorCodes.E5006)
  @Size(min = 6, max = 6, message = ErrorCodes.E5003)
  private String pincode;

  @NotBlank(message = ErrorCodes.E5007)
  @Size(max = 20)
  private String houseNumber;

  @NotBlank(message = ErrorCodes.E5008)
  @Size(max = 30)
  private String area;

  @NotBlank(message = ErrorCodes.E5009)
  @Size(max = 30)
  private String landmark;

  @NotBlank(message = ErrorCodes.E5010)
  private String city;

  @NotBlank(message = ErrorCodes.E5011)
  private String state;

  private Boolean defaultAddress;
}
