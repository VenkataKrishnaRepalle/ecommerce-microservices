package com.pm.spring.ema.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "security.roles")
public class RoleProperties {
    private String user;
    private String admin;
    private String customer;
    private String customerSupport;
    private String storeManager;
    private String marketingManager;
    private String contentManager;
    private String vendor;
}