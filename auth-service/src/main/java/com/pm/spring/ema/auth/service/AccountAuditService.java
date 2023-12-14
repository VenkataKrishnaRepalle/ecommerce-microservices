package com.pm.spring.ema.auth.service;

import com.pm.spring.ema.auth.dto.response.UserAuditResponseDto;
import java.util.List;
import java.util.UUID;

public interface AccountAuditService {

    List<UserAuditResponseDto> getAuditRecords(UUID entityId);
}
