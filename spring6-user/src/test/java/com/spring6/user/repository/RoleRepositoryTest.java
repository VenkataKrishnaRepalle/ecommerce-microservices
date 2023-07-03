package com.spring6.user.repository;


import com.spring6.user.entity.Role;
import com.spring6.user.enums.RoleType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testCreateRole() {
        Role role = roleRepository.save(getRole());

        assertThat(role).isNotNull();
        assertThat(role.getId()).isNotNull();
    }
    private Role getRole() {
        return Role.builder()
                .name(RoleType.ADMIN)
                .description("Manage Everything")
                .build();
    }
}