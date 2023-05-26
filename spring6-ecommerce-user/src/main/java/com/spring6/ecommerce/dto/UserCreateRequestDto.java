package com.spring6.ecommerce.dto;

import com.spring6.ecommerce.entity.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class UserCreateRequestDto {
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
