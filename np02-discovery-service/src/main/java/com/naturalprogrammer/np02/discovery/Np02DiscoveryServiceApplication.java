package com.naturalprogrammer.np02.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Np02DiscoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(Np02DiscoveryServiceApplication.class, args);
	}
}
