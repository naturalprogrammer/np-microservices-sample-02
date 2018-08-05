package com.naturalprogrammer.np02.profile.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naturalprogrammer.np02.profile.domain.Profile;
import com.naturalprogrammer.np02.profile.forms.ProfileForm;
import com.naturalprogrammer.np02.profile.services.ProfileService;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/profiles")
@AllArgsConstructor
public class ProfileController {

	private static final Log log = LogFactory.getLog(ProfileController.class);
	
	private ProfileService profileService;

	@PostMapping
	public Mono<Profile> upsertProfile(@RequestBody @Validated Mono<ProfileForm> profile) {
		
		log.debug("Signing up: " + profile);
		return profileService.upsertProfile(profile);
	}
}
