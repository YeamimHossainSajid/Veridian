package com.veridian.apigateway.filter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "veridian.jwt")
public record JwtProperties(String secret) {
}
