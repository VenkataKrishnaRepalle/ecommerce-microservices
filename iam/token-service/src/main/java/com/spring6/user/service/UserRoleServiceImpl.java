package com.pm.spring.ema.user.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRepository userRepository;
    private final com.pm.spring.ema.user.model.repository.TokenRepository tokenRepository;

    public UserRoleServiceImpl(UserRepository userRepository, com.pm.spring.ema.user.model.repository.TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void assignRoleToUser(UUID userId, UUID roleId) {
        UserProfile userProfile = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Role role = tokenRepository.findById(roleId).orElseThrow(() -> new EntityNotFoundException("Role not found"));

        userProfile.getRoles().add(role);
        userRepository.save(userProfile);
    }

    @Override
    public void removeRoleFromUser(UUID userId, UUID roleId) {
        UserProfile userProfile = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Role role = tokenRepository.findById(roleId).orElseThrow(() -> new EntityNotFoundException("Role not found"));

        userProfile.getRoles().remove(role);
        userRepository.save(userProfile);
    }
}
