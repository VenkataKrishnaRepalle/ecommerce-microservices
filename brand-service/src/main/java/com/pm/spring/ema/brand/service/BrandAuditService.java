package com.pm.spring.ema.brand.service;

import com.pm.spring.ema.brand.dto.response.BrandAuditResponseDto;

import java.util.List;
import java.util.UUID;

public interface BrandAuditService {

    List<BrandAuditResponseDto> getAuditRecords(UUID entityId);
}
