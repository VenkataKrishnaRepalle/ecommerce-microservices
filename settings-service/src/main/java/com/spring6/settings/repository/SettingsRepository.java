package com.pm.spring.ema.settings.repository;

import com.pm.spring.ema.settings.entity.Settings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface SettingsRepository extends JpaRepository<Settings, UUID> {
    Optional<Settings> findByName(String keyName);
}
