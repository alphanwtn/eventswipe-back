package com.M2IProject.eventswipe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GeneralController {

	@RequestMapping("/")
	public String displayIndex() {
		System.out.println("Test de connexion");
		return "index";
	}
}
