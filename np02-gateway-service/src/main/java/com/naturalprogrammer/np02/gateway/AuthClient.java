package com.naturalprogrammer.np02.gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naturalprogrammer.np02.gateway.dto.MyToken;

@FeignClient("auth")
public interface AuthClient {
	
	@RequestMapping(path = "/api/core/fetch-micro-token", method=RequestMethod.GET)
	MyToken fetchMicroToken(@RequestHeader("Authorization") String authorization);
}
