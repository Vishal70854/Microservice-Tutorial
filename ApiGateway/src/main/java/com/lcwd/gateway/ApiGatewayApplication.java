package com.lcwd.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
// NOTE : ApiGateway will be another microservice which we will register in eureka server so that it will take incoming request and pass it on to delegated microservice
@SpringBootApplication
@EnableDiscoveryClient  // this annotation will register this ApiGateway service in Eureka discovery server and will be discoverable to other microservice
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}
