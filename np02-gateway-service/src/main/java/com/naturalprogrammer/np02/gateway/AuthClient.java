package com.naturalprogrammer.np02.gateway;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("auth")
public interface AuthClient {
	
	@GetMapping("/api/core/fetch-micro-token")
	Map<String, String> fetchMicroToken();
}
