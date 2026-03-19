package com.pm.spring.ema.brand.controller;

import com.pm.spring.ema.brand.dto.BrandAuditDto;
import com.pm.spring.ema.brand.service.BrandAuditService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/brand")
@RestController
public class BrandAuditController {

  private final BrandAuditService brandAuditService;

  @GetMapping("{id}/audit")
  public List<BrandAuditDto> getBrandAudits(@PathVariable("id") UUID id) {

    return brandAuditService.getAuditRecords(id);
  }
}
