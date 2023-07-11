package com.spring6.user.dto.response;

import com.spring6.user.model.enums.UserStatus;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class UserCreateResponseDto {
    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private String photo;

    private UserStatus status;

    private Instant createdOn;

    private Instant lastUpdatedOn;

}
