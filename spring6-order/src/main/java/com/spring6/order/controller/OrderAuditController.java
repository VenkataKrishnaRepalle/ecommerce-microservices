package com.spring6.order.controller;

import com.spring6.order.dto.response.OrderAuditResponseDto;
import com.spring6.order.service.OrderAuditService;
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
public class OrderAuditController {

    private final OrderAuditService orderAuditService;

    @GetMapping("{id}/audit")
    public List<OrderAuditResponseDto> getBrandAudits(
            @PathVariable("id") UUID id) {

        return orderAuditService.getAuditRecords(id);
    }
}
