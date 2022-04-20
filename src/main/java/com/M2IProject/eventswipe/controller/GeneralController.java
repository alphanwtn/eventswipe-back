package com.M2IProject.eventswipe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class GeneralController {

    @RequestMapping("/")
    public String displayIndex() {
	System.out.println("Test de connexion");
	return "index";
    }
}
