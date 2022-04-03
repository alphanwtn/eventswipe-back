package com.M2IProject.eventswipe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersEventListEntityController {
	@Autowired
	private com.M2IProject.eventswipe.service.UsersEventListEntityService UsersEventListEntityService;
}
