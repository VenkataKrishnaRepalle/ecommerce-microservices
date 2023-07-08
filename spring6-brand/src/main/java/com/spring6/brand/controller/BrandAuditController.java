package com.spring6.brand.controller;

import com.spring6.brand.dto.BrandAuditResponseDto;
import com.spring6.brand.entity.Brand;
import com.spring6.brand.service.BrandAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<BrandAuditResponseDto> getAuditRecordsForEntity(
            @PathVariable("id") UUID id) {

        return brandAuditService.getAuditRecords(id);
    }
}
