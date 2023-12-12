package com.pm.spring.ema.elastic.query.service.business;

import com.pm.spring.ema.elastic.query.service.dataaccess.entity.UserPermission;

import java.util.List;
import java.util.Optional;

public interface QueryUserService {

    Optional<List<UserPermission>> findAllPermissionsByUsername(String username);
}
