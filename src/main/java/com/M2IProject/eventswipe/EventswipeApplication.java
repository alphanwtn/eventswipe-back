package com.M2IProject.eventswipe;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventswipeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(EventswipeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("<<<<<<<<<<<< Confirmation du lancement de l'app <<<<<<<<<<<<<<<<");
	}
}