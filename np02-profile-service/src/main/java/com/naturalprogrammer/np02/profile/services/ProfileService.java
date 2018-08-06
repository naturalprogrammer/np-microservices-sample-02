package com.naturalprogrammer.np02.profile.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.naturalprogrammer.np02.profile.domain.Profile;
import com.naturalprogrammer.np02.profile.forms.ProfileForm;
import com.naturalprogrammer.np02.profile.repositories.ProfileRepository;
import com.naturalprogrammer.spring.lemon.commons.security.UserDto;
import com.naturalprogrammer.spring.lemon.commons.util.LecUtils;
import com.naturalprogrammer.spring.lemon.commonsreactive.util.LecrUtils;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class ProfileService {
	
	private ProfileRepository profileRepository;
	
	@PreAuthorize("isAuthenticated()")
	public Mono<Profile> upsertProfile(Mono<ProfileForm> profileForm) {
		
		return profileForm.zipWith(LecrUtils.currentUser())
				.flatMap(tuple -> {
					
					UserDto user = tuple.getT2().get();
					ProfileForm form = tuple.getT1();
							
					LecUtils.ensureAuthority(user.isGoodAdmin() || user.getId().equals(
							form.getUserId().toString()),
							"com.naturalprogrammer.spring.notGoodAdminOrSameUser");
					
					return profileRepository.findByUserId(form.getUserId())
							.flatMap(profile -> updateProfile(profile, form))
							.switchIfEmpty(newProfile(form));
				});
			
	}
	
	private Mono<Profile> updateProfile(Profile profile, ProfileForm form) {

		profile.setName(form.getName());
		profile.setWebsite(form.getWebsite());
		profile.setAbout(form.getAbout());
		
		return profileRepository.save(profile);
	}

	private Mono<Profile> newProfile(ProfileForm form) {

		Profile profile = new Profile();
		profile.setUserId(form.getUserId());
		profile.setName(form.getName());
		profile.setWebsite(form.getWebsite());
		profile.setAbout(form.getAbout());
		
		return profileRepository.insert(profile);
	}
}
