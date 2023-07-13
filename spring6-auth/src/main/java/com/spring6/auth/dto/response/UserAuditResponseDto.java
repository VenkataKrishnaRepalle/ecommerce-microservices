package com.spring6.auth.dto.response;

import com.spring6.auth.model.enums.AccountStatus;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.RevisionType;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
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
