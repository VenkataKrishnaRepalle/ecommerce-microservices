package com.spring6.order.dto.response;


import com.spring6.order.model.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class OrderUpdateResponseDto {
    private UUID id;
    private String name;
    private UUID subcategoryId;
    private OrderStatus status;
}
