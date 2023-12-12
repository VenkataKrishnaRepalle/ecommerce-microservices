package com.pm.spring.ema.user.service;

import com.pm.spring.ema.user.model.entity.Role;
import com.pm.spring.ema.user.model.entity.UserProfile;
import com.pm.spring.ema.user.model.repository.RoleRepository;
import com.pm.spring.ema.user.model.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserRoleServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void assignRoleToUser(UUID userId, UUID roleId) {
        UserProfile userProfile = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new EntityNotFoundException("Role not found"));

        userProfile.getRoles().add(role);
        userRepository.save(userProfile);
    }

    @Override
    public void removeRoleFromUser(UUID userId, UUID roleId) {
        UserProfile userProfile = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new EntityNotFoundException("Role not found"));

        userProfile.getRoles().remove(role);
        userRepository.save(userProfile);
    }
}
