package com.pm.spring.ema.auth.service.common.service;

import com.pm.spring.ema.auth.service.common.dto.response.UserAuditResponseDto;
import java.util.List;
import java.util.UUID;

public interface AccountAuditService {

    List<UserAuditResponseDto> getAuditRecords(UUID entityId);
}
