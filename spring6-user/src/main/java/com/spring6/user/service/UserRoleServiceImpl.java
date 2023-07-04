package com.spring6.user.service;

import com.spring6.user.entity.Role;
import com.spring6.user.entity.User;
import com.spring6.user.repository.RoleRepository;
import com.spring6.user.repository.UserRepository;
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
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new EntityNotFoundException("Role not found"));

        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public void removeRoleFromUser(UUID userId, UUID roleId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new EntityNotFoundException("Role not found"));

        user.getRoles().remove(role);
        userRepository.save(user);
    }
}
