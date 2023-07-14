package com.spring6.order.controller;

import com.spring6.order.dto.response.BrandAuditResponseDto;
import com.spring6.order.service.BrandAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/brand")
@RestController
public class BrandAuditController {

    private final BrandAuditService brandAuditService;

    @GetMapping("{id}/audit")
    public List<BrandAuditResponseDto> getBrandAudits(
            @PathVariable("id") UUID id) {

        return brandAuditService.getAuditRecords(id);
    }
}
