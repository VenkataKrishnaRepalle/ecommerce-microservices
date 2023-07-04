package com.spring6.user.service;

import java.util.UUID;

public interface UserRoleService {
    void assignRoleToUser(UUID userId, UUID roleId);
    void removeRoleFromUser(UUID userId, UUID roleId);
}