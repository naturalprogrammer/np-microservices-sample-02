package com.naturalprogrammer.np02.auth.controllers;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.naturalprogrammer.np02.auth.domain.User;
import com.naturalprogrammer.np02.auth.services.MyLemonService;
import com.naturalprogrammer.spring.lemon.commons.security.UserDto;
import com.naturalprogrammer.spring.lemon.commons.util.UserUtils;
import com.naturalprogrammer.spring.lemon.commons.util.UserUtils.SignUpValidation;
import com.naturalprogrammer.spring.lemonreactive.LemonReactiveController;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(MyLemonController.BASE_URI)
@AllArgsConstructor
public class MyLemonController extends LemonReactiveController<User, ObjectId> {

	private static final Log log = LogFactory.getLog(MyLemonController.class);

	public static final String BASE_URI = "/api/core";
	
	private MyLemonService lemonService;
	
	@Override
	public Mono<UserDto<ObjectId>> signup(
			@RequestBody @JsonView(UserUtils.SignupInput.class)
			@Validated(SignUpValidation.class) Mono<User> user,
			ServerHttpResponse response) {
		
		return super.signup(user, response);
	}
	
	/**
	 * A simple function for pinging this server.
	 */
	@GetMapping("/fetch-micro-token")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Mono<Map<String, String>> fetchMicroToken() {
		
		log.debug("Fetching a micro token");
		return lemonService.fetchMicroToken();
	}	
}
