package com.naturalprogrammer.np02.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.naturalprogrammer.np02.lib001.scan.Lib001Configuration;

@SpringBootApplication(scanBasePackageClasses= {
		Np02GatewayServiceApplication.class,
		Lib001Configuration.class
})
@EnableFeignClients
public class Np02GatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(Np02GatewayServiceApplication.class, args);
	}
}
