package com.naturalprogrammer.np02.profile.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.naturalprogrammer.np02.lib001.scan.channels.UserChannel;
import com.naturalprogrammer.np02.lib001.scan.forms.UserMessage;
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
	
	private static final Log log = LogFactory.getLog(ProfileService.class);
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
	
	@StreamListener(UserChannel.INPUT)
	public void subscribeUserMessage(UserMessage userMessage) {
		
		log.debug("Received " + userMessage);

		profileRepository.findByUserId(new ObjectId(userMessage.getId()))
			.doOnNext(profile -> profile.setName(userMessage.getName()))
			.flatMap(profileRepository::save)
			.subscribe();
	}	
}
