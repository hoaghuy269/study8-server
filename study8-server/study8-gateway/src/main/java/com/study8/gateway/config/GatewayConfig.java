package com.study8.gateway.config;

import com.study8.gateway.constant.SysServiceConstant;
import com.study8.gateway.security.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${study8.sys.uri}")
    private String sysUri;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder, AuthenticationFilter filter) {
        return builder.routes()
                .route(SysServiceConstant.SYS_AUTH_ROUTE_ID,r -> r.path(SysServiceConstant.SYS_AUTH_PATH)
                        .filters(f -> f.filter(filter))
                        .uri(sysUri))
                .route(SysServiceConstant.SYS_SYSTEM_ROUTE_ID, r -> r.path(SysServiceConstant.SYS_SYSTEM_PATH)
                        .filters(f -> f.filter(filter))
                        .uri(sysUri))
                .build();
    }
}
