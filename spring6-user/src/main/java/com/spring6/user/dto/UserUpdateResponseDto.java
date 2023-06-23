package com.spring6.user.dto;


import com.spring6.user.entity.Role;
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

    private Boolean isEnabled;

    private Instant createdOn;

    private Instant lastUpdatedOn;

    private Set<Role> roles = new HashSet<>();
}
