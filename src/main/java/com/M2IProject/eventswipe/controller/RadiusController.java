package com.M2IProject.eventswipe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.M2IProject.eventswipe.model.UserEntity;
import com.M2IProject.eventswipe.service.UserEntityService;

@Controller

@CrossOrigin(origins = "*")
@RequestMapping("/radius")

public class RadiusController {
    @Autowired
    UserEntityService userEntityService;

    // creating a put mapping that set or modify the search radius of a user
    @PutMapping("/{userid}/{radius}")
    private @ResponseBody UserEntity updateRadius(@PathVariable("userid") int userid,
	    @PathVariable("radius") int radius) {
	return userEntityService.updateRadius(userid, radius);
    }
}
