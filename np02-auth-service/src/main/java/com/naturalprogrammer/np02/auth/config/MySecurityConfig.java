package com.naturalprogrammer.np02.auth.config;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import com.naturalprogrammer.np02.auth.domain.User;
import com.naturalprogrammer.np02.auth.domain.User.Tag;
import com.naturalprogrammer.np02.lib001.scan.security.MicroUserDetails;
import com.naturalprogrammer.spring.lemon.commons.security.JwtService;
import com.naturalprogrammer.spring.lemon.commons.security.UserDto;
import com.naturalprogrammer.spring.lemon.commons.util.LecUtils;
import com.naturalprogrammer.spring.lemonreactive.security.LemonReactiveSecurityConfig;
import com.naturalprogrammer.spring.lemonreactive.security.LemonReactiveUserDetailsService;
import com.nimbusds.jwt.JWTClaimsSet;

@Component
public class MySecurityConfig extends LemonReactiveSecurityConfig<User, ObjectId> {

	public MySecurityConfig(JwtService jwtService, LemonReactiveUserDetailsService<User, ObjectId> userDetailsService) {
		super(jwtService, userDetailsService);
	}
	
	protected UserDto<ObjectId> getUserDto(JWTClaimsSet claims) {

		Object userClaim = claims.getClaim(JwtService.USER_CLAIM);
		
		if (userClaim == null)
			return null;
		
		MicroUserDetails userDetails = LecUtils.deserialize((String) userClaim);
		
		UserDto<ObjectId> userDto = new UserDto<>();
		userDto.setId(new ObjectId(userDetails.getId()));
		userDto.setUsername(userDetails.getName());
		userDto.setRoles(userDetails.getRoles());
		
		Tag tag = new Tag();
		tag.setName(userDetails.getName());
		userDto.setTag(tag);
		
		userDto.initialize();
		return userDto;
	}
}
