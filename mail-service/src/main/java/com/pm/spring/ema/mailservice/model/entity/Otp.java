package com.pm.spring.ema.mailservice.model.entity;

import com.pm.spring.ema.mailservice.model.enums.OtpStatus;
import com.pm.spring.ema.mailservice.model.enums.OtpType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(indexes = {
        @Index(name = "index_otp_id", columnList = "id"),
        @Index(name = "index_otp_id_status", columnList = "id, userUuid, status"),
        @Index(name = "index_otp_id_number_status_type", columnList = "id, userUuid, otp, type, status")
})
public class Otp {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID userUuid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status")
    private OtpStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "type")
    private OtpType type;

    @Column(name = "otp", nullable = false)
    private Long otpNumber;

    @CreationTimestamp
    @Column(name = "created_time", nullable = false, updatable = false)
    private Date createdTime;

    @PreUpdate
    @PrePersist
    public void setDefaultStatus() {
        if (status == null) {
            status = OtpStatus.ACTIVE;
        }
    }
}