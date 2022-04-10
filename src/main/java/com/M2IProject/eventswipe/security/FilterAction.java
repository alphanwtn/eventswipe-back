package com.M2IProject.eventswipe.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.M2IProject.eventswipe.repository.UserEntityRepository;

@Service
public class FilterAction {

	@Autowired
	UserEntityRepository userEntityRepository;

	public boolean isSelfUserOrHasAdminRole(int userid) {
		SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String emailRequester = authentication.getName();
		Collection<? extends GrantedAuthority> rolesOfRequester = authentication.getAuthorities();
		String emailOfSearchedUser = userEntityRepository.findById(userid).get().getEmail();

		if (emailRequester.equals(emailOfSearchedUser) || rolesOfRequester.contains(adminAuthority))
			return true;
		else {
			System.err.println("Bad user or not admin for this action");
			return false;
		}
	}

	public boolean hasAdminRole() {
		SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> rolesOfRequester = authentication.getAuthorities();

		if (rolesOfRequester.contains(adminAuthority))
			return true;
		else {
			System.err.println("Only admins authorized for this action");
			return false;
		}

	}

}
