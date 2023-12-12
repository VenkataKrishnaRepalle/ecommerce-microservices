package com.pm.spring.ema.analytics.service.dataaccess.repository;

import com.pm.spring.ema.analytics.service.dataaccess.entity.MessageAnalyticsEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AnalyticsRepository extends JpaRepository<MessageAnalyticsEntity, UUID>,
        AnalyticsCustomRepository<MessageAnalyticsEntity, UUID> {

    @Query(value = "select e from MessageAnalyticsEntity e where e.word=:word order by e.recordDate desc")
    List<MessageAnalyticsEntity> getAnalyticsEntitiesByWord(@Param("word") String word, Pageable pageable);
}
