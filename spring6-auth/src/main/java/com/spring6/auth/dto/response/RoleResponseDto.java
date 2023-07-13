package com.spring6.auth.dto.response;

import com.spring6.auth.model.entity.Permission;
import com.spring6.auth.model.enums.RoleType;
import lombok.Builder;
import lombok.Data;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
public class RoleResponseDto {

     private UUID id;
     private RoleType name;
    private String description;
    private Instant createdOn;
    private Instant lastUpdatedOn;
}
