package com.pm.spring.ema.common.util.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDto {

  private UUID id;

  @NotNull private String email;

  @NotNull @JsonIgnore private String password;

  @NotNull private String firstName;

  @NotNull private String lastName;

  @NotNull private String phoneNumber;

  @NotNull private String addressLine1;

  private String addressLine2;

  @NotNull private String city;

  @NotNull private String state;

  @NotNull private String country;

  @NotNull private String postalCode;

  @NotNull private EnabledStatus isEnabled;

  private LocalDateTime createdTime;

  private LocalDateTime updatedTime;
}
