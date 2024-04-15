package com.lcwd.user.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient	// this annotation will register this userService in Eureka service and will be discoverable to other microservice
@EnableFeignClients		// this annotation is used to enable the Feign client inorder to be able to make http api calls to other microservice
public class UserServiceApplication {



	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
