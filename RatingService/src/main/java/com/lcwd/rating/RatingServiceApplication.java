package com.lcwd.rating;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient  // this annotation will register this userService in Eureka service and will be discoverable to other microservice
public class RatingServiceApplication {


	public static void main(String[] args) {
		SpringApplication.run(RatingServiceApplication.class, args);
	}

}
