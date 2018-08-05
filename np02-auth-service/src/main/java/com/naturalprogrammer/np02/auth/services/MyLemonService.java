package com.naturalprogrammer.np02.auth.services;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.naturalprogrammer.np02.auth.domain.User;
import com.naturalprogrammer.np02.auth.domain.User.Tag;
import com.naturalprogrammer.np02.lib001.scan.security.MicroUserDetails;
import com.naturalprogrammer.spring.lemon.commons.security.JwtService;
import com.naturalprogrammer.spring.lemon.commons.security.UserDto;
import com.naturalprogrammer.spring.lemon.commons.util.LecUtils;
import com.naturalprogrammer.spring.lemonreactive.LemonReactiveService;
import com.naturalprogrammer.spring.lemonreactive.util.LerUtils;

import reactor.core.publisher.Mono;

@Service
public class MyLemonService extends LemonReactiveService<User, ObjectId> {

	public static final String ADMIN_NAME = "Administrator";

	@Override
	public User newUser() {
		return new User();
	}

    @Override
    protected User createAdminUser() {
    	
    	User user = super.createAdminUser(); 
    	user.setName(ADMIN_NAME);
    	return user;
    }
    
	@Override
    protected void updateUserFields(User user, User updatedUser, UserDto<ObjectId> currentUser) {

        super.updateUserFields(user, updatedUser, currentUser);
        user.setName(updatedUser.getName());
    }

	@PreAuthorize("isAuthenticated()")
	public Mono<Map<String, String>> fetchMicroToken() {

		return LerUtils.currentUser().map(optionalUser -> {
			
			UserDto<?> currentUser = optionalUser.get();
			
			MicroUserDetails userDetails = new MicroUserDetails();
			userDetails.setId(((ObjectId) currentUser.getId()).toString());
			userDetails.setName(((Tag)currentUser.getTag()).getName());
			userDetails.setRoles(currentUser.getRoles());
			userDetails.setUsername(currentUser.getUsername());
			
			Map<String, Object> claimMap = Collections.singletonMap(JwtService.USER_CLAIM, LecUtils.serialize(userDetails));
			
			Map<String, String> tokenMap = Collections.singletonMap("token", LecUtils.TOKEN_PREFIX +
				jwtService.createToken(JwtService.AUTH_AUDIENCE, userDetails.getUsername(),
						Long.valueOf(properties.getJwt().getShortLivedMillis()),
						claimMap));
			
			return tokenMap;
		});
	}
}
