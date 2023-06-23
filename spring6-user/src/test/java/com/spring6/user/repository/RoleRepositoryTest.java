package com.spring6.user.repository;

import com.spring6.user.entity.Role;
import com.spring6.user.entity.RoleType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testCreateBrand() {
        Role role = roleRepository.save(Role.builder()
                .name(RoleType.ADMIN)
                .description("Manage Everything")
                .build());

        assertThat(role).isNotNull();
        assertThat(role.getId()).isNotNull();

    }
}