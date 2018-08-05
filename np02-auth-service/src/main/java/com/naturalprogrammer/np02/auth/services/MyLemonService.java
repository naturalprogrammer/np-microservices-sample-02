package com.naturalprogrammer.np02.auth.services;

import java.util.Collections;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.naturalprogrammer.np02.auth.domain.User;
import com.naturalprogrammer.np02.lib001.scan.security.UserTag;
import com.naturalprogrammer.spring.lemon.commons.security.JwtService;
import com.naturalprogrammer.spring.lemon.commons.security.LemonPrincipal;
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
    protected void updateUserFields(User user, User updatedUser, UserDto currentUser) {

        super.updateUserFields(user, updatedUser, currentUser);
        user.setName(updatedUser.getName());
    }

	@Override
	protected ObjectId toId(String id) {
		return new ObjectId(id);
	}
}
