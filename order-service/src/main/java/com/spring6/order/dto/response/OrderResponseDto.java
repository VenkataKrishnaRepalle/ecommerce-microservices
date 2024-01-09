package com.spring6.order.dto.response;


import com.spring6.order.model.entity.OrderDetail;
import com.spring6.order.model.enums.OrderStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
    private UUID id;

    private UUID userId;

    private BigDecimal productTotalCost;

    private BigDecimal shippingCost;

    private BigDecimal tax;

    private BigDecimal total;

    private OrderStatus status;

    @NotEmpty
    private List<OrderDetail> orderDetails;

    private Instant createdTime;


    private Instant lastUpdatedTime;
}
