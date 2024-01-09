package com.spring6.order.service;


import com.spring6.order.dto.response.OrderAuditResponseDto;

import java.util.List;
import java.util.UUID;

public interface OrderAuditService {

    List<OrderAuditResponseDto> getAuditRecords(UUID entityId);
}
