package com.study8.process;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableProcessApplication
@EnableDiscoveryClient
public class Study8ProcessApplication {

	public static void main(String[] args) {
		SpringApplication.run(Study8ProcessApplication.class, args);
	}

}
