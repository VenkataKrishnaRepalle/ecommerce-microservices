package com.pm.spring.ema.client.service.model.entity;

import com.pm.spring.ema.client.service.model.enums.AuthorizationGrantTypeEnum;
import com.pm.spring.ema.client.service.model.enums.ClientAuthenticationMethodEnum;
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
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Client {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String secret;

    @ElementCollection
    @CollectionTable(name = "scopes", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "scope")
    private List<String> scopes;

    @ElementCollection(targetClass = ClientAuthenticationMethodEnum.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "Client_authentication_methods")
    @Column(name = "Client_authentication_method")
    private Set<ClientAuthenticationMethodEnum> clientAuthenticationMethods;

    @ElementCollection(targetClass = AuthorizationGrantTypeEnum.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "authorization_grant_types")
    @Column(name = "authorization_grant_type")
    private Set<AuthorizationGrantTypeEnum> authorizationGrantTypes;

    @ElementCollection
    @CollectionTable(name = "redirect_uris", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "redirect_uri")
    private List<String> redirectUris;

    @ElementCollection
    @CollectionTable(name = "postLogoutRedirectUris", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "postLogoutRedirectUri")
    private Set<String> postLogoutRedirectUris;

    @CreationTimestamp
    private Instant createdOn;

    @UpdateTimestamp
    private Instant lastUpdatedOn;

    @CreationTimestamp
    private Instant clientIdIssuedAt;
    private Instant clientSecretExpiresAt;

}