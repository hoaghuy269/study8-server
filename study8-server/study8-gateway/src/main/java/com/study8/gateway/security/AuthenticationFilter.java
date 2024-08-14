package com.study8.gateway.security;

import com.study8.gateway.jwt.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * AuthenticationFilter
 * @Date: 2024-08-14
 * @Author: HuyNH
 * @Desc: Authentication Filter
 */
@RefreshScope
@Component
public class AuthenticationFilter implements GatewayFilter {
    @Autowired
    private JwtUtils jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (RouterValidator.isSecured.test(request)) {
            if (this.isAuthMissing(request))
                return this.onError(exchange);

            final String token = this.getAuthHeader(request);

            if (jwtUtil.isInvalid(token))
                return this.onError(exchange);

            this.populateRequestWithHeaders(exchange, token);
        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        String header = request
                .getHeaders()
                .getOrEmpty("Authorization")
                .get(0);
        return header.startsWith("Bearer ")
                ? header.substring(7)
                : header;
    }


    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders()
                .containsKey("Authorization");
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        Claims claims = jwtUtil.getAllClaimsFromToken(token);
        exchange.getRequest().mutate()
                .header("id", String.valueOf(claims.get("id")))
                .header("role", String.valueOf(claims.get("role")))
                .build();
    }
}
