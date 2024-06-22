package com.study8.gateway.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * AuthEntryPointJwt
 * @Date: 2024-06-17
 * @Author: HuyNH
 * @Desc: Auth Entry Point Jwt
 */
@Component
@Slf4j
public class AuthEntryPointJwt implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException authException) {
        log.error("AuthEntryPointJwt | Unauthorized error: {}", authException.getMessage());

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.UNAUTHORIZED.value());
        body.put("error", "Unauthorized");
        body.put("message", authException.getMessage());
        body.put("path", exchange.getRequest().getURI().getPath());

        try {
            ObjectMapper mapper = new ObjectMapper();
            byte[] responseBody = mapper.writeValueAsBytes(body);
            DataBuffer dataBuffer = response.bufferFactory().wrap(responseBody);
            return response.writeWith(Mono.just(dataBuffer));
        } catch (IOException e) {
            log.error("Error writing response body: {}", e.getMessage());
            return Mono.error(e);
        }
    }
}
