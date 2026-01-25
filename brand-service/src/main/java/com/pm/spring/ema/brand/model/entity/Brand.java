package com.pm.spring.ema.brand.model.entity;

import com.pm.spring.ema.common.util.enums.BrandStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Audited
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "brand", indexes = {@Index(name = "idx__brand__id_name", columnList = "id, name")})
@Entity
public class Brand {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, length = 45, unique = true)
    private String name;

    @Column(length = 128)
    private String imageName;

    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    private BrandStatusEnum status;

    @CreationTimestamp
    private Instant createdOn;

    @UpdateTimestamp
    private Instant lastUpdatedOn;

    @Column(nullable = false, length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID subcategoryId;

}


