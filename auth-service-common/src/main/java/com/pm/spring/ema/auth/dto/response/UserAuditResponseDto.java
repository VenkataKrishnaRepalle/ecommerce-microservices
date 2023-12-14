package com.pm.spring.ema.auth.dto.response;

import com.pm.spring.ema.auth.dto.enums.AccountStatus;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.RevisionType;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class UserAuditResponseDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private AccountStatus status;
    private Instant createdOn;
    private Instant lastUpdatedOn;

    private Number revision;
    private RevisionType revisionType;
}
