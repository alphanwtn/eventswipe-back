package com.M2IProject.eventswipe.service.securityservices;

import java.util.Collection;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.M2IProject.eventswipe.repository.UserEntityRepository;

@Service
public class FilterActionService {

	@Autowired
	UserEntityRepository userEntityRepository;

	/**
	 * Si le userid qui fait la requete correspond à l'userid rentré en param cela
	 * renvoie true si le user est un admin, cette fonction renverra toujours true
	 * 
	 * @param userid
	 * @return
	 */
	public boolean doToSelfOrAdminRole(int userid) {
		SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String emailRequester = authentication.getName();
		Collection<? extends GrantedAuthority> rolesOfRequester = authentication.getAuthorities();
		String emailOfSearchedUser = userEntityRepository.findById(userid).get().getEmail();

		if (emailRequester.equals(emailOfSearchedUser) || rolesOfRequester.contains(adminAuthority))
			return true;
		else
			return false;
	}

}
