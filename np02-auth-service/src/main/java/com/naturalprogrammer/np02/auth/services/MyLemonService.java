package com.naturalprogrammer.np02.auth.services;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.naturalprogrammer.np02.auth.domain.User;
import com.naturalprogrammer.spring.lemon.commons.security.UserDto;
import com.naturalprogrammer.spring.lemonreactive.LemonReactiveService;

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
}
