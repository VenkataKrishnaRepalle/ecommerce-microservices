package com.pm.spring.ema.authentication.common.model.repository;

import com.pm.spring.ema.authentication.common.model.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PermissionRepository extends JpaRepository<Privilege, UUID> {
}