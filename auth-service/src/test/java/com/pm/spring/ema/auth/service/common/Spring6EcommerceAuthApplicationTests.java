package com.pm.spring.ema.auth.service.common;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.SecureRandom;
import java.util.Base64;

@SpringBootTest
class Spring6EcommerceAuthApplicationTests {

    @Test
    void contextLoads() {

    }

    @Test
    void testLogic() {
        System.out.println("Hi");
    }

    @Test
    void testGenerateJWTToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[36]; // 36 bytes * 8 = 288 bits, a little bit more than
        // the 256 required bits
        random.nextBytes(bytes);
        var encoder = Base64.getUrlEncoder().withoutPadding();
        System.out.println(encoder.encodeToString(bytes));
    }

}
