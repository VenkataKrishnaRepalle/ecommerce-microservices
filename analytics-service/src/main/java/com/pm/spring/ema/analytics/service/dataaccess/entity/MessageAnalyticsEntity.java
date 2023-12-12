package com.pm.spring.ema.analytics.service.dataaccess.entity;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@Table(name = "message_analytics")
public class MessageAnalyticsEntity implements BaseEntity<UUID> {

    @Id
    @NotNull
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @NotNull
    @Column(name = "word")
    private String word;

    @NotNull
    @Column(name = "word_count")
    private Long wordCount;

    @NotNull
    @Column(name = "record_date")
    private LocalDateTime recordDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageAnalyticsEntity that = (MessageAnalyticsEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
