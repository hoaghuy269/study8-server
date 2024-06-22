package com.study8.sys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Study8SysApplication {

    public static void main(String[] args) {
        SpringApplication.run(Study8SysApplication.class, args);
    }

}