package com.spring6.user.dto;

import com.spring6.user.entity.Role;
import com.spring6.user.entity.UserStatus;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class UserFindResponseDto {
    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private String imageUrl;

    private UserStatus status;

    private Instant createdOn;

    private Instant lastUpdatedOn;
}
