package com.study8.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * SecurityAdapter
 * @Date: 2024-06-17
 * @Author: HuyNH
 * @Desc: Web Security Config
 */
@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {
    @Autowired
    AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(unauthorizedHandler))
                .authorizeExchange(authorize -> authorize
                        .pathMatchers("/sys/api/v1/auth/**").permitAll()
                        .pathMatchers("/sys/api/v1/system/**").permitAll()
                        .anyExchange().authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
