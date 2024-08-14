package com.study8.gateway.security;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

/**
 * RouterValidator
 * @Date: 2024-08-14
 * @Author: HuyNH
 * @Desc: Router Validator
 */
@Component
public class RouterValidator {
    public static final List<String> openApiEndpoints = List.of(
            "/sys/api/v1/auth/register",
            "/sys/api/v1/auth/login"
    );

    public static final Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
