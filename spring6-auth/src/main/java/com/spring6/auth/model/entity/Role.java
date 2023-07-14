package com.spring6.auth.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.spring6.auth.model.enums.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Audited
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role", indexes = {@Index(name = "idx_role_id", columnList = "id"), @Index(name = "idx_role_name", columnList = "name")})
@Entity
public class Role {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(length = 40, unique = true, nullable = false)
    private RoleType name;

    @Column(length = 150, nullable = false)
    private String description;

    @CreationTimestamp
    private Instant createdOn;

    @UpdateTimestamp
    private Instant lastUpdatedOn;

    // @JsonIgnore
    @JsonBackReference
    @ManyToMany(mappedBy = "roles")
    private Set<Account> accounts = new HashSet<>();

    //    @JsonIgnore
    @JsonManagedReference
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @JoinTable(
            name = "role_privilege",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id")
    )
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Privilege> privileges = new HashSet<>();

}
