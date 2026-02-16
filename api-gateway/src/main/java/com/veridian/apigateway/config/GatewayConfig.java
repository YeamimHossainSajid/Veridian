package com.veridian.apigateway.config;

import com.veridian.apigateway.filter.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class GatewayConfig {
}
