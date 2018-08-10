package com.naturalprogrammer.np02.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;

import com.naturalprogrammer.np02.lib001.scan.Lib001Configuration;
import com.naturalprogrammer.np02.lib001.scan.channels.UserChannel;

@SpringBootApplication(scanBasePackageClasses= {
		Np02AuthServiceApplication.class,
		Lib001Configuration.class
})
@EnableFeignClients
@EnableBinding({UserChannel.class, Processor.class})
public class Np02AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(Np02AuthServiceApplication.class, args);
	}
}
