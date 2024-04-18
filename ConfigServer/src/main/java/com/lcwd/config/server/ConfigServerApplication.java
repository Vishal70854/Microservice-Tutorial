package com.lcwd.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer // this annotation is used to enable configuration server
//@EnableDiscoveryClient	// this annotation is not mandatory as by adding eureka client dependency in pom.xml this annotation is implicitly used by spring boot on startup
public class ConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
	}

}
