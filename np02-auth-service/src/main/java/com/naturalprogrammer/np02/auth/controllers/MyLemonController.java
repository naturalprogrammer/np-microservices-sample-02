package com.naturalprogrammer.np02.auth.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.naturalprogrammer.np02.auth.domain.User;
import com.naturalprogrammer.np02.auth.services.MyLemonService;
import com.naturalprogrammer.np02.lib001.scan.forms.UserMessage;
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
	
	private MyLemonService myService;

	public static final String BASE_URI = "/api/core";
	
	@Override
	public Mono<UserDto> signup(
			@RequestBody @JsonView(UserUtils.SignupInput.class)
			@Validated(SignUpValidation.class) Mono<User> user,
			ServerHttpResponse response) {
		
		return super.signup(user, response);
	}

	@PostMapping("/users/{id}/name")	
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Mono<Void> updateName(
			@PathVariable String id,
			@RequestBody @Validated Mono<UserMessage> userForm,
			ServerHttpResponse response) {
		
		return myService.updateName(id, userForm);
	}
}
