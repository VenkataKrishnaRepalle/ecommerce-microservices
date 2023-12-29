package com.pm.spring.ema.client.service.model.entity;

import com.pm.spring.ema.client.service.converter.GrantTypeConverter;
import com.pm.spring.ema.client.service.converter.StringListConverter;
import com.pm.spring.ema.client.service.dto.enums.GrantType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Scope {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;
    private String name;
    private String clientSecret;

    @Convert(converter = GrantTypeConverter.class)
    @Column(name = "grant_type")
    private List<GrantType> grantTypes;

    @Convert(converter = StringListConverter.class)
    @Column(name = "scope")
    private List<String> scopes;

    @Convert(converter = StringListConverter.class)
    @Column(name = "redirect_uri")
    private List<String> redirectUris;

    @CreationTimestamp
    private Instant createdOn;

    @UpdateTimestamp
    private Instant lastUpdatedOn;

}