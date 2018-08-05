package com.naturalprogrammer.np02.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.naturalprogrammer.np02.lib001.scan.Lib001Configuration;

@SpringBootApplication(scanBasePackageClasses= {
		Np02AuthServiceApplication.class,
		Lib001Configuration.class
})
@EnableFeignClients
public class Np02AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(Np02AuthServiceApplication.class, args);
	}
}
