package com.spring6.order.service;

import com.spring6.order.dto.response.BrandAuditResponseDto;

import java.util.List;
import java.util.UUID;

public interface BrandAuditService {

    List<BrandAuditResponseDto> getAuditRecords(UUID entityId);
}
