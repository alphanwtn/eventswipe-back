package com.M2IProject.eventswipe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.M2IProject.eventswipe.model.UserEntity;
import com.M2IProject.eventswipe.service.UserEntityService;

@RestController
public class UserEntityController {
	@Autowired
	UserEntityService UserEntityService;

	// creating a get mapping that retrieves all the users detail from the database
	@GetMapping("/users")
	private List<UserEntity> getAllUserEntity() {
		return UserEntityService.getAllUserEntity();
	}

	// creating a get mapping that retrieves the detail of a specific user
	@GetMapping("/users/{userid}")
	private UserEntity getUsers(@PathVariable("userid") int userid) {
		return UserEntityService.getUserEntityById(userid);
	}

	// creating a delete mapping that deletes a specified user
	@DeleteMapping("/users/{userid}")
	private List<UserEntity> deleteUserEntity(@PathVariable("userid") int userid) {
		UserEntityService.delete(userid);
		return UserEntityService.getAllUserEntity();
	}

	// creating post mapping that post the user detail in the database
	@PostMapping("/user")
	private int saveUser(@RequestBody UserEntity user) {
		UserEntityService.saveOrUpdate(user);
		return user.getId();
	}

	// creating put mapping that updates the user detail
	@PutMapping("/user")
	private UserEntity update(@RequestBody UserEntity user) {
		UserEntityService.saveOrUpdate(user);
		return user;
	}

}