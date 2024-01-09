package com.spring6.order.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailsCreateRequestDto {
    private UUID productId;

    private int quantity;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String secondaryPhoneNumber;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String district;

    private String state;

    private String country;

    private String postalCode;
}
