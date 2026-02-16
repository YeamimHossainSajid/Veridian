package com.veridian.apigateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Component
public class JwtAuthenticationGatewayFilterFactory
        extends AbstractGatewayFilterFactory<JwtAuthenticationGatewayFilterFactory.Config> {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationGatewayFilterFactory.class);
    private static final String BEARER_PREFIX = "Bearer ";
    private final SecretKey jwtSecretKey;

    public JwtAuthenticationGatewayFilterFactory(JwtProperties jwtProperties) {
        super(Config.class);
        this.jwtSecretKey = Keys.hmacShaKeyFor(
                jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (!StringUtils.hasText(authHeader) || !authHeader.startsWith(BEARER_PREFIX)) {
                return unauthorized(exchange.getResponse(), "Missing or invalid Authorization header");
            }
            String token = authHeader.substring(BEARER_PREFIX.length()).trim();
            try {
                Claims claims = Jwts.parser()
                        .verifyWith(jwtSecretKey)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();
                ServerHttpRequest request = exchange.getRequest().mutate()
                        .header("X-User-Id", nullSafe(claims.get("sub")))
                        .header("X-User-Name", nullSafe(claims.get("preferred_username")))
                        .header("X-Roles", nullSafe(claims.get("roles")))
                        .build();
                return chain.filter(exchange.mutate().request(request).build());
            } catch (Exception e) {
                log.debug("JWT validation failed: {}", e.getMessage());
                return unauthorized(exchange.getResponse(), "Invalid or expired token");
            }
        };
    }

    private static String nullSafe(Object o) {
        if (o == null) return "";
        if (o instanceof List<?> list) return String.join(",", list.stream().map(Object::toString).toList());
        return o.toString();
    }

    private static Mono<Void> unauthorized(ServerHttpResponse response, String message) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String body = "{\"type\":\"about:blank\",\"title\":\"Unauthorized\",\"status\":401,\"detail\":\"" + message + "\"}";
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }

    public static class Config {
        // optional route-specific config
    }
}
