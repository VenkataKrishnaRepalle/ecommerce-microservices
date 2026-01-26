package com.pm.spring.ema.brand.service;

import com.pm.spring.ema.brand.dto.BrandAuditDto;

import java.util.List;
import java.util.UUID;

public interface BrandAuditService {

    List<BrandAuditDto> getAuditRecords(UUID entityId);
}
