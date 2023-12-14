package com.pm.spring.ema.auth.controller;

import com.pm.spring.ema.auth.dto.response.UserAuditResponseDto;
import com.pm.spring.ema.auth.service.AccountAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class AccountAuditController {

    private final AccountAuditService accountAuditService;

    @GetMapping("{id}/audit")
    public List<UserAuditResponseDto> getUserAudits(@PathVariable("id") UUID id) {

        return accountAuditService.getAuditRecords(id);
    }
}
