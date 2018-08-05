package com.naturalprogrammer.np02.lib001.scan.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.naturalprogrammer.spring.lemon.commons.security.LemonGrantedAuthority;
import com.naturalprogrammer.spring.lemon.commons.util.LecUtils;
import com.naturalprogrammer.spring.lemon.commons.util.UserUtils;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MicroUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 6024800553135900137L;
	
	private String id;
	private String username;
	private String name;
	private Set<String> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Collection<LemonGrantedAuthority> authorities = roles.stream()
				.map(role -> new LemonGrantedAuthority("ROLE_" + role))
				.collect(Collectors.toCollection(() ->
					new ArrayList<LemonGrantedAuthority>(roles.size() + 2))); 

		boolean unverified = hasRole(UserUtils.Role.UNVERIFIED);
		boolean blocked = hasRole(UserUtils.Role.BLOCKED);
		boolean admin = hasRole(UserUtils.Role.ADMIN);
		boolean goodUser = !(unverified || blocked);
		boolean goodAdmin = goodUser && admin;

		if (goodUser) {
			
			authorities.add(new LemonGrantedAuthority("ROLE_"
					+ LecUtils.GOOD_USER));
			
			if (goodAdmin)
				authorities.add(new LemonGrantedAuthority("ROLE_"
					+ LecUtils.GOOD_ADMIN));
		}
		
		return authorities;	
	}

	public final boolean hasRole(String role) {
		return roles.contains(role);
	}	

	@Override
	public String getPassword() {

		return null;
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {

		return true;
	}	
}
