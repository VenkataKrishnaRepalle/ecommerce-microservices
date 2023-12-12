package com.pm.spring.ema.auth.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pm.spring.ema.auth.model.enums.TokenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import java.time.Instant;
import java.util.UUID;

@Audited
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "token", indexes = {@Index(name = "idx_token_id", columnList = "id")})
@Entity
public class Token {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(unique = true)
    public String token;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    public boolean revoked;

    public boolean expired;

    @CreationTimestamp
    private Instant createdOn;

    @UpdateTimestamp
    private Instant lastUpdatedOn;

    // @JsonIgnore
    @JoinColumn(name = "account_id")
    @ManyToOne
    private Account account;

}
