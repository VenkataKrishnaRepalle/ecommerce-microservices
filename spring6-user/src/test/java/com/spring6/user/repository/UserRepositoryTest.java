package com.spring6.user.repository;

import com.spring6.user.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateBrand() {
        User savedUser = userRepository.save(getUser());

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();

    }
    private User getUser() {

        return User.builder()
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

    private Set<Privilege> getPrivileges() {

        Set<Privilege> privileges = new HashSet<>();

        privileges.add(Privilege.builder().name("Read").build());
        privileges.add(Privilege.builder().name("Write").build());
        privileges.add(Privilege.builder().name("Delete").build());

        return privileges;
    }


}