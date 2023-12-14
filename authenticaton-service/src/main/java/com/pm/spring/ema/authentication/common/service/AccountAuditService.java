package com.pm.spring.ema.authentication.common.service;

import com.pm.spring.ema.authentication.common.dto.response.UserAuditResponseDto;
import java.util.List;
import java.util.UUID;

public interface AccountAuditService {

    List<UserAuditResponseDto> getAuditRecords(UUID entityId);
}
