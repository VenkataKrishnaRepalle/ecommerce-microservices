package com.pm.spring.ema.order.service;

import com.pm.spring.ema.order.dto.response.OrderAuditResponseDto;

import java.util.List;
import java.util.UUID;

public interface OrderAuditService {

    List<OrderAuditResponseDto> getAuditRecords(UUID entityId);
}
