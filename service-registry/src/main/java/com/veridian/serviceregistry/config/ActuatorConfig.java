package com.veridian.serviceregistry.config;

import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActuatorConfig {

    @Bean
    public HealthIndicator registryHealthIndicator() {
        return () -> org.springframework.boot.actuate.health.Health.up()
                .withDetail("service", "service-registry")
                .build();
    }
}
