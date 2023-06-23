package com.spring6.user.repository;

import com.spring6.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateBrand() {
        User savedUser = userRepository.save(User.builder()
                .firstName("Acer")
                .photo("Photo.png")
                .build());

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();

    }

}