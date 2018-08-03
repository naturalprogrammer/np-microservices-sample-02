package com.naturalprogrammer.np02.auth.controllers;

import org.bson.types.ObjectId;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.naturalprogrammer.np02.auth.controllers.domain.User;
import com.naturalprogrammer.spring.lemon.commons.security.UserDto;
import com.naturalprogrammer.spring.lemon.commons.util.UserUtils;
import com.naturalprogrammer.spring.lemon.commons.util.UserUtils.SignUpValidation;
import com.naturalprogrammer.spring.lemonreactive.LemonReactiveController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(MyLemonController.BASE_URI)
public class MyLemonController extends LemonReactiveController<User, ObjectId> {

	public static final String BASE_URI = "/api/core";

	@Override
	public Mono<UserDto<ObjectId>> signup(
			@RequestBody @JsonView(UserUtils.SignupInput.class)
			@Validated(SignUpValidation.class) Mono<User> user,
			ServerHttpResponse response) {
		
		return super.signup(user, response);
	}
}
