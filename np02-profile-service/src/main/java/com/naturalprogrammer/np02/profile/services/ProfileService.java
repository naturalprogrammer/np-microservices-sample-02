package com.naturalprogrammer.np02.profile.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.naturalprogrammer.np02.profile.domain.Profile;
import com.naturalprogrammer.np02.profile.forms.ProfileForm;
import com.naturalprogrammer.np02.profile.repositories.ProfileRepository;
import com.naturalprogrammer.spring.lemon.commons.util.LecUtils;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class ProfileService {
	
	private ProfileRepository profileRepository;
	
	@PreAuthorize("isAuthenticated()")
	public Mono<Profile> upsertProfile(Mono<ProfileForm> profileForm) {
		
		return profileForm.zipWith()
				.map(LecUtils.ensureAuthority(authorized, messageKey))
				.flatMap(form -> {
					return profileRepository.findByUserId(form.getUserId())
							.zipWith(Mono.just(form));
				});
			
	}

}
