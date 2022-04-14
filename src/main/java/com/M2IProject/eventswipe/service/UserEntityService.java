package com.M2IProject.eventswipe.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.M2IProject.eventswipe.model.ERole;
import com.M2IProject.eventswipe.model.RoleEntity;
import com.M2IProject.eventswipe.model.UserEntity;
import com.M2IProject.eventswipe.repository.UserEntityRepository;

@Service
public class UserEntityService {
	@Autowired
	UserEntityRepository UserEntityRepository;

//	@Autowired
//	FilterAction filterActionService;

	@Autowired
	PasswordEncoder passwordEncoder;

	// getting all users record by using the method findaAll() of CrudRepository
	public List<UserEntity> getAllUserEntity() {
		List<UserEntity> users = new ArrayList<UserEntity>();
		UserEntityRepository.findAll().forEach(user -> users.add(user));
		return users;
	}

	// getting a specific record by using the method findById() of CrudRepository
	public UserEntity getUserEntityById(int userid) {

		return UserEntityRepository.findById(userid).get();
	}

	// saving a specific record by using the method save() of CrudRepository
	public void save(UserEntity user) {
		RoleEntity userrole = new RoleEntity(2, ERole.ROLE_USER); // role user by default
		Set<RoleEntity> roles = new HashSet<>();
		roles.add(userrole);

		String pwd = user.getPassword();
		user.setPassword(passwordEncoder.encode(pwd));
		user.setRoles(roles);
		UserEntityRepository.save(user);

	}

	// deleting a specific record by using the method deleteById() of CrudRepository
	public void delete(int id) {
		UserEntityRepository.deleteById(id);

	}

	// updating a record
	public void update(UserEntity user) {
		UserEntityRepository.save(user);
	}

}
