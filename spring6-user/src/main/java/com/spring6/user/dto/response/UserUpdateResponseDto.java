package com.spring6.user.dto.response;


import com.spring6.user.model.entity.Role;
import com.spring6.user.model.enums.UserStatus;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class UserUpdateResponseDto {
    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private String password;

    private String photo;

    private UserStatus status;

    private Instant createdOn;

    private Instant lastUpdatedOn;

    private Set<Role> roles = new HashSet<>();
}
