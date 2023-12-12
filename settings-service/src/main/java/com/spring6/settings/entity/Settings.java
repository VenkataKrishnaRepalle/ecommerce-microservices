package com.pm.spring.ema.settings.entity;

import com.pm.spring.ema.common.enums.SettingCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Settings {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(length = 45, nullable = false, unique = true)
    private String keyName;

    @Column(length = 45, nullable = false, unique = true)
    private String valueField;

    @Enumerated(EnumType.STRING)
    @Column(length = 40, nullable = false)
    private SettingCategory category;


    @CreationTimestamp
    @Column(name = "created_time", updatable = false)
    private Date createdTime;

    @UpdateTimestamp
    @Column(name = "updated_time")
    private Date updatedTime;
}
