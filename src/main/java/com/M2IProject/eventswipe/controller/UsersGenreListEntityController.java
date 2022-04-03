package com.M2IProject.eventswipe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersGenreListEntityController {
	@Autowired
	private com.M2IProject.eventswipe.service.UsersGenreListEntityService UsersGenreListEntityService;
}
