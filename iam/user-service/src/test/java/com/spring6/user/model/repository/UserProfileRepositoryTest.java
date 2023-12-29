package com.pm.spring.ema.user.model.repository;

import com.pm.spring.ema.user.model.enums.RoleType;
import com.pm.spring.ema.user.model.enums.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserProfileRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateBrand() {
        com.pm.spring.ema.user.model.entity.User savedUser = userRepository.save(getUser());

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();

    }
    private com.pm.spring.ema.user.model.entity.User getUser() {

        return com.pm.spring.ema.user.model.entity.User.builder()
                .firstName("Rajesh")
                .lastName("kumar")
                .username("rajeshkumar")
                .email("rajesh@gmail.com")
                .password("rajesh@123")
                .roles(getRoles())
                .status(UserStatus.ACTIVE)
                .photo("Photo.png")
                .build();
    }

    private Set<Role> getRoles() {
        Set<Role> roles = new HashSet<>();

        roles.add(Role.builder().name(RoleType.ADMIN).description("Manage Everything").build());
        roles.add(Role.builder().name(RoleType.SALES_PERSON).description("Manage Sales and Report").build());
        return roles;
    }

    private Set<Permission> getPrivileges() {

        Set<Permission> privileges = new HashSet<>();

        privileges.add(Permission.builder().name("Read").build());
        privileges.add(Permission.builder().name("Write").build());
        privileges.add(Permission.builder().name("Delete").build());

        return privileges;
    }


}