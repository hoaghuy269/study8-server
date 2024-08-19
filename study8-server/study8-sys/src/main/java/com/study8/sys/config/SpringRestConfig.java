package com.study8.sys.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

/**
 * SpringRestConfig
 * @Date: 2024-08-19
 * @Author: HuyNH
 * @Desc: Spring Rest Config
 */
@Configuration
public class SpringRestConfig implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(
            RepositoryRestConfiguration config,
            CorsRegistry cors) {
        config.disableDefaultExposure();
    }
}
