package com.spring6.brand.service;

import com.spring6.brand.dto.BrandAuditResponseDto;
import com.spring6.brand.entity.Brand;

import java.util.List;
import java.util.UUID;

public interface BrandAuditService {

    List<BrandAuditResponseDto> getAuditRecords(UUID entityId);
}
