package com.pm.spring.ema.user.role.common.repository;

import com.pm.spring.ema.user.role.common.entity.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, UUID> {
}
