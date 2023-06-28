package com.spring6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Spring6CustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring6CustomerApplication.class, args);
//		String password = "myPassword123";
//
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		String hashedPassword = passwordEncoder.encode(password);
//
//		System.out.println("Plain password: " + password);
//		System.out.println("Hashed password: " + hashedPassword);
//
//		// Verify password
//		boolean isMatch = passwordEncoder.matches(password, hashedPassword);
//		System.out.println("Password matches: " + isMatch);
	}
}
