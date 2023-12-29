package com.pm.spring.ema.user.service;

import com.pm.spring.ema.user.model.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRepository userRepository;
    private final com.pm.spring.ema.user.model.repository.UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRepository userRepository, com.pm.spring.ema.user.model.repository.UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void assignRoleToUser(UUID userId, UUID roleId) {
        com.pm.spring.ema.user.model.entity.User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Role role = userRoleRepository.findById(roleId).orElseThrow(() -> new EntityNotFoundException("Role not found"));

        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public void removeRoleFromUser(UUID userId, UUID roleId) {
        com.pm.spring.ema.user.model.entity.User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Role role = userRoleRepository.findById(roleId).orElseThrow(() -> new EntityNotFoundException("Role not found"));

        user.getRoles().remove(role);
        userRepository.save(user);
    }
}
