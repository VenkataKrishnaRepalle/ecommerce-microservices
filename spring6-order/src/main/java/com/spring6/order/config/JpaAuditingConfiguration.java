package com.spring6.order.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfiguration {

//    @Bean
//    public AuditorAware<UUID> auditorAware() {
//        return new UserAuditorAware();
//    }


}
