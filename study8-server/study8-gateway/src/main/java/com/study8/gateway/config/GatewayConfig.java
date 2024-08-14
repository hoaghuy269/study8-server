package com.study8.gateway.config;

import com.study8.gateway.security.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * GatewayConfig
 * @Date: 2024-08-14
 * @Author: HuyNH
 * @Desc: Gateway Config
 */
@Configuration
@EnableHystrix
public class GatewayConfig {
    @Autowired
    AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("study8-sys-auth", r -> r.path("/sys/api/v1/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://study8-sys"))
                .route("study8-sys-system", r -> r.path("/sys/api/v1/system/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://study8-sys"))
                .build();
    }
}
