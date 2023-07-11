package com.spring6.auth.service;

import com.spring6.auth.dto.response.UserAuditResponseDto;
import java.util.List;
import java.util.UUID;

public interface AccountAuditService {

    List<UserAuditResponseDto> getAuditRecords(UUID entityId);
}
