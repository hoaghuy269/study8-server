package com.study8.gateway.security;

import com.study8.gateway.constant.SysServiceConstant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RouterValidator {
    public static final List<String> openApiEndpoints = List.of(
            SysServiceConstant.SYS_AUTH_API_LOGIN
    );

    public static final Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
