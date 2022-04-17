package com.M2IProject.eventswipe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.M2IProject.eventswipe.model.UserEntity;
import com.M2IProject.eventswipe.service.UserEntityService;

@RestController
@RequestMapping(path = "/users")
@CrossOrigin(origins = "*")
public class UserEntityController {
	@Autowired
	UserEntityService userEntityService;

//	@Autowired
//	FilterAction filterActionService;

	// creating a get mapping that retrieves all the users detail from the database
	@GetMapping
	// @PreAuthorize("hasRole('ADMIN')")
	private List<UserEntity> getAllUserEntity() {
		return userEntityService.getAllUserEntity();
	}

	// creating a get mapping that retrieves the detail of a specific user
	@GetMapping("/{userid}")
	private UserEntity getUsers(@PathVariable("userid") int userid) {
		return userEntityService.getUserEntityById(userid);
	}

	// creating post mapping that post the user detail in the database

	@PostMapping
	private int saveUser(@RequestBody UserEntity user) {
		userEntityService.save(user);
		return user.getId();
	}

	// creating a delete mapping that deletes a specified user
	@DeleteMapping("/{userid}")
	private List<UserEntity> deleteUserEntity(@PathVariable("userid") int userid) {
		userEntityService.delete(userid);
		return userEntityService.getAllUserEntity();
	}

	// creating put mapping that updates the user detail
	@PutMapping
	private UserEntity update(@RequestBody UserEntity user) {
		userEntityService.update(user);
		return user;
	}

	// creating a put mapping that set or modify the search radius of a user
	@PutMapping("/gps/{userid}/{latitude}/{longitude}")
	private @ResponseBody UserEntity updateGPSCoordinates(@PathVariable("userid") int userid,
			@PathVariable("longitude") String longitude, @PathVariable("latitude") String latitude) {
		return userEntityService.updateGPSCoordinates(userid, longitude, latitude);
	}

	// creating a put mapping that set or modify the search radius of a user
	@PutMapping("/radius/{userid}/{radius}")
	private @ResponseBody UserEntity updateRadius(@PathVariable("userid") int userid,
			@PathVariable("radius") int radius) {
		return userEntityService.updateRadius(userid, radius);
	}

}