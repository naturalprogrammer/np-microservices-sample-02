package com.naturalprogrammer.np02.profile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

import com.naturalprogrammer.np02.lib001.scan.channels.UserChannel;

@SpringBootApplication
@EnableBinding(UserChannel.class)
public class Np02ProfileServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(Np02ProfileServiceApplication.class, args);
	}
}
