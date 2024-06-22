package com.study8.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Study8ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(Study8ApiApplication.class, args);
    }

}
